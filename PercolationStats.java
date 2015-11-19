
public class PercolationStats {  
      
    private double mean;  
    private double stddev;  
    private int T;  
      
    // Perform T independent computational experiments on an N-by-N grid  
    public PercolationStats(int N, int T) {   
        if (N <= 0 || T <= 0)  
            throw new java.lang.IllegalArgumentException();  
        this.T = T;  
        double[] x = new double[T];  
        for (int t = 0; t < T; t++) {  
            Percolation percolation = new Percolation(N);  
            for (int c = 0; c < N * N; c++) {  
                // Choose a random site  
                int i = StdRandom.uniform(N) + 1;  
                int j = StdRandom.uniform(N) + 1;  
                // If the site is open, choose another random site  
                while (percolation.isOpen(i, j)) {  
                    i = StdRandom.uniform(N) + 1;  
                    j = StdRandom.uniform(N) + 1;  
                }  
                percolation.open(i, j); //if the site is not open, open it  
                if (percolation.percolates()) {  // If this site percolates, calculate res   
                    x[t] = (c + 0.0) / (N * N);  
                    break;  
                }  
            }  
        }  
        // Mean(using stdStats API may be better!)  
        for (int t = 0; t < T; t++)  
            mean += x[t];  
        mean /= T;  
        // Stddev(using stdStats API may be better!)  
        for (int t = 0; t < T; t++)  
            stddev += (x[t] - mean) * (x[t] - mean);  
        stddev /= (T - 1);  
        stddev = Math.sqrt(stddev);  
    }  
  
    public double mean() { // sample mean of percolation threshold  
        return mean;  
    }  
  
    public double stddev() { // sample standard deviation of percolation threshold  
        return stddev;  
    }  
  
    public double confidenceLo() { // returns lower bound of the 95% confidence interval  
        return mean - 1.96 * stddev / Math.sqrt(T);  
    }  
  
    public double confidenceHi() { // returns upper bound of the 95% confidence interval  
        return mean + 1.96 * stddev / Math.sqrt(T);  
    }  
  
    public static void main(String[] args) { // test client, described below  
            int N = StdIn.readInt();    
            int T = StdIn.readInt();  
            Stopwatch timer = new Stopwatch();  
            PercolationStats ps = new PercolationStats(N, T);  
        double time = timer.elapsedTime();  
        System.out.println("time                    = " + time);  
        System.out.println("mean                    = " + ps.mean());  
        System.out.println("stddev                  = " + ps.stddev());  
        System.out.println("95% confidence interval = " + ps.confidenceLo() + ", " + ps.confidenceHi());  
    }  
}  