
import java.util.Scanner;

public class WeightedUnionFind
{
    private int[] id;
    private int[] size;
    private int count;

    public WeightedUnionFind(int N){
        id = new int[N];
        size = new int[N];
        count = N;
        for(int i=0;i<N;i++){
            id[i]=i;size[i]=1;
        }
    }

    public int root(int i){
        while(i!=id[i]){
            id[i] = id[id[i]];
            i = id[i];
        }
        return i;
    }

    public boolean connected(int p,int q){
        return id[p]==id[q];
    }

    public void union(int p,int q){
        int i = root(p);
        int j = root(q);
        if(i==j) return;
        if(size[i]<size[j]){ id[i] = j; size[j] += size[i];}
        else               { id[j] = i; size[i] += size[j];}
        count--;
    }

    public int count(){
        return count;
    }

    public void main(String[] args){
    	Scanner in = new Scanner(System.in);
        int N = in.nextInt();
        WeightedUnionFind uf = new WeightedUnionFind(N);
        while(in.hasNext()){
            int p = in.nextInt();
            int q = in.nextInt();
            if(uf.connected(p,q)) continue;
            uf.union(p,q);
            System.out.println(p+" "+q);
        }
        System.out.println(uf.count()+" components!");
    }
}

