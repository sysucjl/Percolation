/*************************************************************************
	> File Name: PercolationStats.java
	> Author:sysucjl 
	> Mail:cjl091454@gmail.com 
	> Created Time: 2015年08月25日 星期二 23时05分14秒
 ************************************************************************/

public class PercolationStats
{
    private double[] x;
    private double mean;
    private double stddev;

    public PercolationStats(int N, int T)     // perform T independent experiments on an N-by-N grid
    {
        x = new double[T];
        for(int i=0;i<T;i++){
            Percolation per = new Percolation(N);
            int count=0;
            while(!per.percolates()){
                int row = (int) (Math.random()*N) + 1;
                int col = (int) (Math.random()*N) + 1;
                while(per.isOpen(row,col)){
                     row = (int) (Math.random()*N) + 1;
                     col = (int) (Math.random()*N) + 1;
                }
                per.open(row,col);
                count++;
            }
            x[i] = count*1.0/(N*N);
        }
        double sum = 0.0;
        for(int i=0;i<T;i++)
            sum += x[i];
        mean = sum / T;

        double squareSum = 0.0;
        for(int i=0;i<T;i++)
            squareSum += (x[i] - mean)*(x[i] - mean);
        stddev = Math.sqrt(squareSum / (T-1));
    }
    public double mean()                      // sample mean of percolation threshold
    {
        return mean;

    }
    public double stddev()                    // sample standard deviation of percolation threshold
    {
        return stddev;
    }
    public double confidenceLo()              // low  endpoint of 95% confidence interval
    {
        return (mean - 1.96*stddev/Math.sqrt(x.length));
    }
    public double confidenceHi()              // high endpoint of 95% confidence interval
    {
        return (mean + 1.96*stddev/Math.sqrt(x.length));
    }

    public static void main(String[] args)    // test client (described below)
    {
        if(args.length != 2){
            System.out.println("Usage:java PercolationStats operand1 operand2");
            System.exit(0);
        }

        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        PercolationStats ps = new PercolationStats(N,T);
        System.out.printf("mean                   \t= %f\n",ps.mean());
        System.out.printf("stddev                 \t= %f\n",ps.stddev());
        System.out.printf("95 confidence interval\t= (%f,%f)\n",ps.confidenceLo(),ps.confidenceHi());
    }
}
