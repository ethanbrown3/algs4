package perc;


import edu.princeton.cs.algs4.Stopwatch;
import edu.princeton.cs.introcs.StdIn;
import edu.princeton.cs.introcs.StdOut;
import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

/**
 * The Class PercolationStats.
 *
 */
public class PercolationStats {

	/** The result array. */
	private double[] resultArray;

	/** The total Sites opened. */
	private int totalOpenSites;

	/** The n for the n by n grid. */
	private int N;

	/** The count of how many simulations. */
	private int count;

	/**
	 * Instantiates a new percolation stats.
	 *
	 * @param N
	 *            the n
	 * @param T
	 *            the t
	 */
	public PercolationStats(int N, int T) {
		if (N <= 0 || T <= 0) { // Check to make sure the user wasn't being an
								// idiot
			throw new java.lang.IllegalArgumentException();
		}

		resultArray = new double[T];
		totalOpenSites = 0;
		count = 0;
		this.N = N;

		// Run the simulation T times
		for (int i = 0; i < T; i++) {
			int result = simulate(N);

			// Store result in resultArray
			resultArray[i] = (double) result / N / N;

			totalOpenSites = totalOpenSites + result;
			count++;
		}

	}

	/**
	 * Simulate the percolation.
	 *
	 * @param N
	 *            the n
	 * @return the int
	 */
	private int simulate(int N) { // Runs simulation on grid size N*N
		int openSites = 0;
		Percolation simulation = new Percolation(N);
		while (!simulation.percolates()) { // opens sites until the grid percolates
			int i = StdRandom.uniform(N); // Random x coordinate
			int j = StdRandom.uniform(N); // Random y coordinate
			if (!simulation.isOpen(i, j)) { // Checks that site isn't open already
				openSites++;
				simulation.open(i, j); // Open the site
			}
		}
		return openSites; // returns total open sites
	}

	/**
	 * Mean for the simulation by taking the number of sites that were opened
	 * and dividing by number of simulations ran and the grid size.
	 * 
	 * @return the double
	 */
	public double mean() {
		return (double) totalOpenSites / count / (N * N);
	}

	/**
	 * Gets the standard deviation.
	 *
	 * @return the std dev
	 */
	public double getStdDev() {
		if (count != 1) {
			return StdStats.stddev(resultArray); // Finds standard deviation of
													// results
		} else {
			return Double.NaN; // Return NaN if count is equal to 1
		}

	}

	/**
	 * The main method. Runs the Monte Carlo Simulation
	 * 
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		String cont = "";
		int N;
		int T;
		do {
		StdOut.println("Enter a value for N");
		N = StdIn.readInt();
		StdOut.println("Enter a value for T");
		T = StdIn.readInt();
		
		// timer just for fun
		Stopwatch sw = new Stopwatch();
		// PercolationStats object
		PercolationStats monteCarlo = new PercolationStats(N, T);
		double totalTime = sw.elapsedTime();

		// Print results
		double mean = monteCarlo.mean();
		double stdDev = monteCarlo.getStdDev();
		double low = (mean - (1.96 * stdDev) / Math.sqrt(T));
		double high = (mean + (1.96 * stdDev) / Math.sqrt(T));
		StdOut.println("Mean Percolation Threshold: \t" + mean);
		StdOut.println("Standard Deviation: \t" + stdDev);
		StdOut.println("Confidence High: \t" + high);
		StdOut.println("Confidence Low: \t" + low);
		StdOut.println("Total Time to Run: \t" + totalTime + " seconds");
		StdOut.println("run again? (Y/N): ");
		cont = StdIn.readString();
		} while (cont.equalsIgnoreCase("Y") || cont.equalsIgnoreCase("yes"));
		StdOut.println("Goodbye");
		
		

	}
}