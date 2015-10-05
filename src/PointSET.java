import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;


public class PointSET {
	SET<Point2D> set;
	private class Node {
		Node right;
		Node left;
		Point2D p;
		RectHV rect;
		public Node(Point2D key) {
			this.p = key;
		}
	}
	public PointSET() {
		// construct an empty set of points
		set = new SET<Point2D>();
	}
	public boolean isEmpty() {
		   // is the set empty? 
		return set.isEmpty();
	}
	public int size() {
		   // number of points in the set
		return set.size();
	}
	public void insert(Point2D p) {
		// add the point to the set (if it is not already in the set)
		set.add(p);
	}
	public boolean contains(Point2D p) {
		   // does the set contain point p?
		return set.contains(p);
	}
	public void draw() {
		   // draw all points to standard draw
		StdDraw.setXscale();
		StdDraw.setYscale();
		StdDraw.setPenRadius(0.01);
		StdDraw.setPenColor(StdDraw.BLACK);
		for (Point2D p: set) {
			StdDraw.point(p.x(), p.y());
		}
		StdDraw.show();
	}
	public Iterable<Point2D> range(RectHV rect) {
		   // all points that are inside the rectangle
		Bag<Point2D> result = new Bag<Point2D>();
		for (Point2D p: set) {
			if(rect.distanceSquaredTo(p) == 0) {
				result.add(p);
			}
		}
		return result;
	}
	public Point2D nearest(Point2D p) {
		   // a nearest neighbor in the set to point p; null if the set is empty 
		Point2D champion = null;
		double mindist = Double.POSITIVE_INFINITY;
		for (Point2D q: set) {
			double dist = p.distanceSquaredTo(q);
			if(dist < mindist) {
				mindist = dist;
				champion = q;
			}
		}
		return champion;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
