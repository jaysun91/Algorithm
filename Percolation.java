
public class Percolation {
   private int size;
   // create N-by-N grid, with all sites blocked
   private boolean [][] grid = null;
   private WeightedQuickUnionUF open = null;
   private WeightedQuickUnionUF full = null;
   private int top;
   private int bottom ;
    public Percolation(int N) {
    	if (N <= 0) {throw new IllegalArgumentException();}
       this.size = N;
       this.top = 0;
       this.bottom = N * N + 1;
	   grid = new boolean[N][N];
	   for (int i = 0; i < N; i++) {
		   for (int j = 0; j < N; j++) {
			   grid[i][j] = false;
		   }
	   }
	   open = new WeightedQuickUnionUF(size * size + 2);
	   full = new WeightedQuickUnionUF(size * size + 2);
   }

   private boolean valid(int i, int j) { 
	   boolean result = false;
	   if (i < 1 || j < 1 || i > size || j > size) {throw new IndexOutOfBoundsException(); }
	   result = true;
	   return result;
   }
   //map from a 2-dimensional (row, column) pair to a 1-dimensional union find object index
   private int xyto1d(int i, int j) {
	   return (i - 1) * size + j;
   }
// open site (row i, column j) if it is not open already
   public void open(int i, int j) {
	   if (valid(i, j)) {
		   grid[i - 1][j - 1] = true;
		   if (i == 1) {
			   open.union(top, xyto1d(i, j));
			   full.union(top, xyto1d(i, j));
		   }
		   else {
			   if (grid[i - 2][j - 1]) {
				   open.union(xyto1d(i - 1,j),xyto1d(i, j));
				   full.union(xyto1d(i - 1,j),xyto1d(i, j));
			   }
		   }
		   if (i == size) {
			   open.union(bottom, xyto1d(i, j));
		   }
		   else {
			   if (grid[i][j - 1]) {
				   open.union(xyto1d(i + 1,j),xyto1d(i, j));
				   full.union(xyto1d(i + 1,j),xyto1d(i, j));
			   }
		   }
		   if (j > 1) {
			   if (grid[i - 1][j - 2]){
				   open.union(xyto1d(i,j - 1), xyto1d(i, j));
				   full.union(xyto1d(i,j - 1), xyto1d(i, j));
			   }
		   }
		   if (j < size){
			   if (grid[i - 1][j]){
				   open.union(xyto1d(i, j + 1), xyto1d(i, j));
				   full.union(xyto1d(i, j + 1), xyto1d(i, j));
			   }
		   }
//		   if (valid(i-1,j) && grid[i-2][j-1] == true) {union.union(xyto1d(i-1, j),xyto1d(i, j));}
//		   if (valid(i+1,j )&& grid[i][j-1] == true) {union.union(xyto1d(i+1, j),xyto1d(i, j));}
//		   if (valid (i,j-1) && grid[i-1][j-2] == true ) {union.union(xyto1d(i, j-1),xyto1d(i, j));}
//		   if (valid (i,j+1) && grid[i-1][j] == true) {union.union(xyto1d(i, j+1),xyto1d(i, j));}
	   }
   }        
    // is site (row i, column j) open?
   public boolean isOpen(int i, int j) {
	return grid[i - 1][j - 1] == true;
	}    
    // is site (row i, column j) full?
   public boolean isFull(int i, int j) {
	   return valid(i , j) && grid [i - 1][j - 1] && full.connected(top, xyto1d(i, j));
   }
   // does the system percolate?
   public boolean percolates() {
	   return open.connected(top, bottom);
	}         
    
   // test client (optional)
   public static void main(String[] args) {
	   Percolation test = new Percolation(4);
	   test.open(1, 1);
	   test.open(1, 2);
	  // System.out.print(test.isOpen(0, 1));
	   boolean test1 = test.full.connected(1, 2);
	   System.out.print(test1);
   }   
}
 