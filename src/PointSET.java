import edu.princeton.cs.algs4.Point2D;


public class PointSET {
	Node root = null;
	private class Node {
		Node right;
		Node left;
		Point2D key;
		private int size = 0;
		boolean color;
		public Node(Point2D key) {
			this.key = key;
		}
		public int size() {
			return size;
		}
	}
	public PointSET() {
		// construct an empty set of points
	}
	public boolean isEmpty() {
		   // is the set empty? 
		return root == null;
	}
	public int size() {
		   // number of points in the set
		if (isEmpty()) return 0;
		return root.size();
	}
	public void insert(Point2D p) {
		// add the point to the set (if it is not already in the set)
		
	}
	public boolean contains(Point2D p) {
		   // does the set contain point p? 
	}
	public void draw() {
		   // draw all points to standard draw 
	}
	public Iterable<Point2D> range(RectHV rect) {
		   // all points that are inside the rectangle 
	}
	public Point2D nearest(Point2D p) {
		   // a nearest neighbor in the set to point p; null if the set is empty 
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
