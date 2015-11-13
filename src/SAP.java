import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;


public class SAP {
    private static final int INFINITY = Integer.MAX_VALUE;
	private Digraph G;
	private int[] distTo1;
	private int[] distTo2;
	private boolean[] marked1;
	private boolean[] marked2;
	// constructor takes a digraph (not necessarily a DAG)
	public SAP(Digraph G) {
		if (G == null) throw new NullPointerException();
		this.G = new Digraph(G);
		marked1 = new boolean[G.V()];
		marked2 = new boolean[G.V()];
		distTo1 = new int[G.V()];
		distTo2 = new int[G.V()];
		for (int i = 0; i < G.V(); i++) {
			distTo1[i] = INFINITY;
			distTo2[i] = INFINITY;
			marked1[i] = false;
			marked2[i] = false;
		}
	}
	
	// length of shortest ancestral path between v and w; -1 if no such path
	public int length(int v, int w) {
		if (v < 0 || v > G.V()-1 || w < 0 || w > G.V()-1) throw new IndexOutOfBoundsException();
		if (v == w) return 0;
		Bag<Integer> a = new Bag<Integer>();
		Bag<Integer> b = new Bag<Integer>();
		a.add(v);
		b.add(w);
		return search(a, b)[1];
	}
	
	// a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
	public int ancestor(int v, int w) {
		if (v < 0 || v > G.V()-1 || w < 0 || w > G.V()-1) throw new IndexOutOfBoundsException();
		if (v == w) return v;
		Bag<Integer> a = new Bag<Integer>();
		Bag<Integer> b = new Bag<Integer>();
		a.add(v);
		b.add(w);
		return search(a, b)[0];
	}
	
	// length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
	public int length(Iterable<Integer> v, Iterable<Integer> w) {
		if (v == null || w == null) throw new NullPointerException();
		for (int i: v) {
			for (int j: w) {
				if (i == j) return 0;
			}
		}
		return search(v, w)[1];
	}
	
	// a common ancestor that participates in shortest ancestral path; -1 if no such path
	public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
		if (v == null || w == null) throw new NullPointerException();
		for (int i: v) {
			for (int j: w) {
				if (i == j) return i;
			}
		}
		return search(v, w)[0];
	}
	
	// Do a breadth first search with two sets of starting points, and look for when the two sets meet
	private int[] search(Iterable<Integer> v, Iterable<Integer> w) {
		if (v == null || w == null) throw new NullPointerException();
		Queue<Integer> queue = new Queue<Integer>();
		SET<Integer> visited = new SET<Integer>();
		for (int i: v) {
			if (i < 0 || i > G.V()-1) throw new IndexOutOfBoundsException();
			visited.add(i);
			queue.enqueue(i);
			marked1[i] = true;
			distTo1[i] = 0;
		}
		for (int i: w) {
			if (i < 0 || i > G.V()-1) throw new IndexOutOfBoundsException();
			visited.add(i);
			queue.enqueue(i);
			marked2[i] = true;
			distTo2[i] = 0;
		}
		int[] result = new int[] {-1, -1};
		int mindist = INFINITY-1;
		while (!queue.isEmpty()) {
			int node = queue.dequeue();
			for (int i: G.adj(node)) {
				/*if (distTo[node] > mindist) {
					// We've exeeded the bound, and won't find a better solution
					cleanup(visited);
					return result;
				}*/
                if (marked1[node] && !marked1[i]) {
                	visited.add(i);
                    distTo1[i] = distTo1[node] + 1;
                    marked1[i] = true;
                    queue.enqueue(i);
                }
                if (marked2[node] && !marked2[i]) {
                	visited.add(i);
                    distTo2[i] = distTo2[node] + 1;
                    marked2[i] = true;
                    queue.enqueue(i);
                }
                if (marked1[i] && marked2[i]) {
                	int d = distTo1[i]+distTo2[i];
                	if (d < mindist) {
                		mindist = d;
                		result = new int[] {i, d};
                	}
                }
			}
		}
		cleanup(visited);
		return result;
	}
	private void cleanup(Iterable<Integer> visited) {
		for (int item: visited) {
			distTo1[item] = INFINITY;
			distTo2[item] = INFINITY;
			marked1[item] = false;
			marked2[item] = false;
		}
	}
	public static void main(String[] args) {
		In in = new In(args[0]);
	    Digraph G = new Digraph(in);
	    SAP sap = new SAP(G);
	    while (!StdIn.isEmpty()) {
	        int v = StdIn.readInt();
	        int w = StdIn.readInt();
	        int length   = sap.length(v, w);
	        int ancestor = sap.ancestor(v, w);
	        StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
	    }

	}

}
