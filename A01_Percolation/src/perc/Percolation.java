/**
 * 
 */
package perc;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * @author ethanbrown
 * 
 */
public class Percolation {
	
	private int[] sites;
	private final int CLOSED = 0;
	private final int OPEN = 1;
	private final int TOP = 2;
	private final int BOTTOM = 4;
	private final int TOP_ONLY = OPEN | TOP;
	private final int TOP_BOTTOM = OPEN | BOTTOM | TOP;
	
	private int openSites = 0;
	private int N;
	private boolean percolates = false;
	
	private WeightedQuickUnionUF ufHelper;
	
	public Percolation(int N){
		this.N = N;
		this.sites = new int[N*N];
		this.ufHelper = new WeightedQuickUnionUF(N * N);
	}

	public void open(int row, int col) {
		validate(row, col);
		int status = CLOSED;
		int site1d = xyto1d(row, col);
		
		if (sites[site1d] == CLOSED) {
			sites[site1d] = OPEN;
			openSites++;
			if (row == 0) {
				status |= TOP;
				sites[site1d] |= TOP;
			}
			if (row == N - 1) {
				status |= BOTTOM;
				sites[site1d] |= BOTTOM;
			}
		}

		if ((row + 1) < N) { 
			if (checkStatus(sites[xyto1d(row + 1,col)], OPEN)) {  		// check the status of the neighbor to see if it is open
				status |= sites[ufHelper.find(xyto1d(row + 1,col))]; 	// save the status of the neighbor site so we can use that status for the newly opened site
				ufHelper.union(site1d, xyto1d(row + 1, col));				// unite the sites!
			}
		}
		if ((row - 1) >= 0) { 
			if (checkStatus(sites[xyto1d(row - 1,col)], OPEN)) {
				status |= sites[ufHelper.find(xyto1d(row - 1,col))];
				ufHelper.union(site1d, xyto1d(row - 1, col));
			}
		}
		if ((col + 1) < N) { 
			if (checkStatus(sites[xyto1d(row,col + 1)], OPEN)) {
				status |= sites[ufHelper.find(xyto1d(row,col + 1))];
				ufHelper.union(site1d, xyto1d(row, col + 1));
			}
		}
		if ((col - 1) >= 0) { 
			if (checkStatus(sites[xyto1d(row,col - 1)], OPEN)) {
				status |= sites[ufHelper.find(xyto1d(row,col - 1))];
				ufHelper.union(site1d, xyto1d(row, col - 1));
			}
		}
		sites[ufHelper.find(xyto1d(row,col))] |= status;  // set the status of the newly opened site to match the status of his neighbors
		if (status == TOP_BOTTOM) {
			percolates = true;
		}
	}
	
	public int numberOfOpenSites() {
		return openSites;
	}
	
	public boolean isFull(int row, int col) {
		validate(row, col);
		return checkStatus(sites[ufHelper.find(xyto1d(row, col))], TOP_ONLY);
	}
	
	public boolean isOpen(int row, int col) {
		validate(row, col);
		return checkStatus(sites[xyto1d(row, col)], OPEN);
	} 
	
	public boolean percolates() {
		return percolates;
	}
	
	private static boolean checkStatus(int status, int statusCheck) {
		return ((status & statusCheck) == statusCheck);
	}
	
	private void validate(int i, int j) {
		if (i < 0 || i > N || j < 0 || j > N)
			throw new IndexOutOfBoundsException("Invalid Row or Column Index.");
	}
	
	private int xyto1d(int row, int col) {
		return row * N + col;
	}
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
