/*************************************************************************
	> File Name: Percolation.java
	> Author:sysucjl 
	> Mail:cjl091454@gmail.com 
	> Created Time: 2015年08月21日 星期五 12时07分30秒
 ************************************************************************/

import java.util.Scanner;


public class Percolation
{
    private WeightedUnionFind uf;
    private boolean[] open;
    private int length; 
    private int Head;
    private int Tail;

    private boolean isValid(int i)
    {
        return (i>0 && i<=length);
    }

    private int position(int i,int j){
        return ((i-1)*length+j-1);
    }

    public Percolation(int N)               // create N-by-N grid, with all sites blocked
    {
        uf = new WeightedUnionFind(N*N+2);  
        open = new boolean[N*N+2];
        length = N;
        Head = N*N;
        Tail = N*N+1;
        for(int i=1;i<=N;i++){
            uf.union(Head,position(1,i));
            uf.union(Tail,position(length,i));
        }
    }

    public void open(int i, int j)          // open site (row i, column j) if it is not open already
    {
        if(isOpen(i,j)) return ;
        int pos = position(i,j);
        open[pos] = true;
        if(isValid(j-1) && isOpen(i,j-1))
            uf.union(pos,position(i,j-1));
        if(isValid(j+1) && isOpen(i,j+1))
            uf.union(pos,position(i,j+1));
        if(isValid(i-1) && isOpen(i-1,j))
            uf.union(pos,position(i-1,j));
        if(isValid(i+1) && isOpen(i+1,j))
            uf.union(pos,position(i+1,j));
    }

    public boolean isOpen(int i, int j)     // is site (row i, column j) open?
    {
        return open[position(i,j)];
    }

    public boolean isFull(int i, int j)     // is site (row i, column j) full?
    {
        return isOpen(i,j)&&uf.connected(position(i,j),Head);
    }

    public boolean percolates()             // does the system percolate?
    {
        for(int i=1;i<=length;i++)
            if(isOpen(1,i)){
                open[Head]=true;
                break;
            }
        return open[Head]&&uf.connected(Head,Tail);
    }

    public void draw(){
        for(int i=0;i<length;i++){
            for(int j=0;j<length;j++){
                if(open[i*length+j]) System.out.print("@");
                else System.out.print("_");
            }
            System.out.print("\n");
        }
    }


    public void test(){
        int p1 =(int) (Math.random() * length)+1;
        int q1 =(int) (Math.random() * length)+1;
        if(isFull(p1,q1)) System.out.printf("Site(%d,%d) is full!\n",p1,q1);
        else System.out.printf("Site(%d,%d) is not full!\n",p1,q1);
    }

    public static void main(String[] args)   // test client (optional)
    {
        Scanner in = new Scanner(System.in);
        int N = in.nextInt();
        Percolation per = new Percolation(N);
        while(in.hasNext()){
            int row = in.nextInt();
            int col = in.nextInt();
            per.open(row,col);
        }
        per.draw();

        if(per.percolates()) System.out.println("This system is percolates!");
        else System.out.println("This system is not percolates!");
        for(int i=0;i<20;i++)
            per.test();
    }
}

