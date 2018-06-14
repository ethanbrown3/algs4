# Approach
Use WeightedQuickUnionFindUF to keep track of which sites are connected
There are 5 different statuses that aren't mutually exclusive that a site could have. For example a site could be both OPEN and FULL or connected to TOP and BOTTOM.
We will use a bit mask to keep track of these statuses for a site. 
```
Site Statuses           bit     int
Closed	                000	0
Open                    001	1
Top     	        010	2 (part of top row)
Full    	        011	3 (connected to top)
Bottom  	        100	4 (part of bottom row)
Percolates      	111	7
```
## Percolation Class
### Constructor
N = size of square board
Sites array =int[N * N] - this will store the status for each site
	(it's tempting to use a 2d array but this will complicate mapping the UF to the array Instead we will convert the coordinates to a 1d array index with the xyto1d method)
Unionfind  - sized to N * N

### Open
	1. Open the cell given the row and col if it isn't open already.
	2. Check that the site it is on the TOP or BOTTOM row and if it is OR it
	3. Union the corresponding cell in the UF to open sites adjacent to the given cell.
	4. If the opened cell's status is 111 then set percolates to TRUE
	
### Xyto1d
Converts a row and col to an index
Return row * N + col
