import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.StdOut;


public class Board {
	private char[] squares;
	private int N;
	
	public Board(int[][] blocks) {
    	// construct a board from an N-by-N array of blocks
    	// (where blocks[i][j] = block in row i, column j)
		if (blocks == null) throw new NullPointerException();
		this.N = blocks.length;
		this.squares = new char[N*N];
		for (int i = 0; i < N; i++) {
			if (blocks[i] == null) throw new NullPointerException();
			for (int j = 0; j < N; j++) {
				squares[i*N+j] = (char) blocks[i][j];
			}
		}

    }
    public int dimension() {
    	// board dimension N
    	return N;
    }
    public int hamming() {
    	// number of blocks out of place
    	int hamming = 0;
    	for (int i = 0; i < N; i++) {
    		for (int j = 0; j < N; j++) {
    			if (squares[i*N+j] != 0 && squares[i*N+j] != ijton(i,j)){
    				hamming++;
    			}
    		}
    	}
    	return hamming;
    }
    public int manhattan() {
    	// sum of Manhattan distances between blocks and goal
    	int manhattan = 0;
    	for (int i = 0; i < N; i++) {
    		for (int j = 0; j < N; j++) {
    			if (squares[i*N+j] != 0) {
    				manhattan += Math.abs(i-ntoi(squares[i*N+j]));
    				manhattan += Math.abs(j-ntoj(squares[i*N+j]));
    			}
    		}
    	}
    	return manhattan;
    	
    }
    public boolean isGoal() {
    	// is this board the goal board?
    	return hamming() == 0;
    }
    public Board twin() {
    	// a board that is obtained by exchanging any pair of blocks
    	int[] ns = new int[2];
    	int n = 0;
    	for (int i = 0; i < N; i++) {
    		for (int j = 0; j < N; j++) {
    			if (ijton(i,j) != squares[i*N+j] && squares[i*N+j] != 0) {
    				ns[n++] = ijton(i,j);
    			}
    			if (n==2) return exch(ns[0],ns[1]);
    		}
    	}
    	for (int i = 0; i < N; i++) {
    		for (int j = 0; j < N; j++) {
    			if (squares[i*N+j] != 0) {
    				ns[n++] = ijton(i,j);
    			}
    			if (n==2) return exch(ns[0],ns[1]);
    		}
    	}
    	return null;
    }
    public boolean equals(Object y) {
    	// does this board equal y?
    	if (this == y) return true;
    	if (y == null) return false;
    	if (y.getClass() != this.getClass()) return false;
    	Board that = (Board) y;
    	if (this.N != that.N) return false;
    	for (int i = 0; i < N; i++) {
    		for (int j = 0; j < N; j++) {
    			if (this.squares[i*N+j] != that.squares[i*N+j]) return false;
    		}
    	}
    	return true;
    }
    public Iterable<Board> neighbors() {
    	// all neighboring boards
    	Bag<Board> neighbors = new Bag<Board>();
    	int n = nof0();
    	if (n > N) neighbors.add(exch(n,n-N));
    	if ((n-1)%N > 0) neighbors.add(exch(n,n-1));
    	if ((n-1)%N < N-1) neighbors.add(exch(n,n+1));
    	if ((n-1) < N*(N-1)) neighbors.add(exch(n,n+N));
    	return neighbors;
    }
    public String toString() {
    	// string representation of this board (in the output format specified below)
    	StringBuilder s = new StringBuilder();
    	s.append(N + "\n");
    	for (int i = 0; i < N; i++) {
    		for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", (int) squares[i*N+j]));
    		}
    		s.append("\n");
    	}
    	return s.toString();
    }
    private int ijton(int i, int j) {
    	return i*N+j+1;
    }
    private int ntoi(int n) {
    	return (n-1)/N;
    }
    private int ntoj(int n) {
    	return (n-1)%N;
    }
    private Board exch(int n1, int n2) {
    	Board that = new Board(this.squaresArray());
    	that.squares[ntoi(n1)*N+ntoj(n1)] = this.squares[ntoi(n2)*N+ntoj(n2)];
    	that.squares[ntoi(n2)*N+ntoj(n2)] = this.squares[ntoi(n1)*N+ntoj(n1)];
    	return that;
    }
    private int nof0() {
    	for (int i = 0; i < N; i++) {
    		for (int j = 0; j < N; j++) {
    			if (squares[i*N+j] == 0) return ijton(i,j);
    		}
    	}
    	return -1;
    }
    private int[][] squaresArray() {
    	int[][] array = new int[N][N]; 
    	for (int i=0; i < N; i++) {
    		for (int j=0; j < N; j++) {
    			array[i][j] = squares[i*N+j];
    		}
    	}
    	return array;
    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int N = 3;
		int[][] sq = new int[N][N];
		for (int i = 0; i<N; i++) {
			for (int j = 0; j<N; j++) {
				sq[i][j] = i*N+j+1;
			}
		}
		sq[2][2] = 0;
		Board def = new Board(sq);
		StdOut.println(def.twin());
		StdOut.println(def.equals(def.exch(1,5)));
		StdOut.println(def.equals(def.exch(1,5).twin()));
		StdOut.println(def.equals(def));
		StdOut.println(def.exch(1,5).hamming());
		StdOut.println(def.exch(1,5).manhattan());
	}
}
