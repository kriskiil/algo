import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;


public class Solver {
	private MinPQ<Node> pq;
	private Node solution;
	private Board initial;
	private class Node implements Comparable<Node> {
		private int key;
		private Board value;
		private Node parent;
		private int moves = -1;
		Node(int key, Board value) {
			this.key = key;
			this.value = value;
		}
		Node(int key, Board value, Node parent) {
			this.key = key;
			this.value = value;
			this.parent = parent;
		}
		public int compareTo(Node that) {
			return Integer.compare(this.key, that.key);
		}
		public int getmoves(){
			if (moves == -1) {
				if (parent == null) {
					moves = 0;
				}
				else {
					moves = 1+parent.getmoves(); 
				}
			}
			return moves;
		}
		public Node getRoot() {
			if (parent == null) return this;
			return parent.getRoot();
		}
		public Board getBoard() {
			return value;
		}
		public Node getParent() {
			return parent;
		}
	}
    public Solver(Board initial) {
    	// find a solution to the initial board (using the A* algorithm)
    	this.initial = initial;
    	Board twin = this.initial.twin();
    	pq = new MinPQ<Node>();
    	pq.insert(new Node(this.initial.manhattan(),this.initial));
    	pq.insert(new Node(twin.manhattan(),twin));
    	while (dequeue() == false) {
    	}
    }
    private boolean dequeue() {
    	Node min = pq.delMin();
    	Board minBoard = min.getBoard();
    	if (minBoard.isGoal()) {
    		solution = min;
    		return true;
    	}
    	if (!(min.getParent() == null)) {
    		Board parent = min.getParent().getBoard();
    		for (Board neighbor: minBoard.neighbors()) {
    			if (!parent.equals(neighbor)){
    				Node n = new Node(neighbor.manhattan()+min.getmoves()+1,neighbor,min);
    				pq.insert(n);
    			}
    		}
    	} else {
    		for (Board neighbor: minBoard.neighbors()) {
    			Node n = new Node(neighbor.manhattan()+min.getmoves()+1,neighbor,min);
    			pq.insert(n);
    		}
    	}

    	return false;
    }
    public boolean isSolvable() {
    	// is the initial board solvable?
    	return initial.equals(solution.getRoot().getBoard());
    }
    public int moves() {
    	// min number of moves to solve initial board; -1 if unsolvable
    	if (isSolvable()) {
    		return solution.getmoves();
    	}
    	return -1;
    }
    public Iterable<Board> solution() {
    	// sequence of boards in a shortest solution; null if unsolvable
    	if (isSolvable()) {
    		Stack<Board> result = new Stack<Board>();
    		Node current = solution;
    		while (current != null) {
    			result.push(current.getBoard());
    			current = current.getParent();
    		}
    		return result;
    	}
    	return null;
    }

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	    // create initial board from file
	    In in = new In(args[0]);
	    int N = in.readInt();
	    int[][] blocks = new int[N][N];
	    for (int i = 0; i < N; i++)
	        for (int j = 0; j < N; j++)
	            blocks[i][j] = in.readInt();
	    Board initial = new Board(blocks);

	    // solve the puzzle
	    Solver solver = new Solver(initial);

	    // print solution to standard output
	    if (!solver.isSolvable())
	        StdOut.println("No solution possible");
	    else {
	        StdOut.println("Minimum number of moves = " + solver.moves());
	        for (Board board : solver.solution())
	            StdOut.println(board);
	    }
		
	}
}
