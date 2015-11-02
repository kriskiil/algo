import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;


public class KdTree {
	private Node root = null;
	private int size = 0;
	private static class Node {
		private Node rb = null;
		private Node lb = null;
		private Point2D p;
		//private RectHV rect;
		public Node(Point2D key) {
			this.p = key;
		}
		public boolean add(Point2D p){
			return add(p,0);
		}
		private boolean add(Point2D p, int h){
			boolean cmp;
			if (p.equals(this.p)) return false;
			if (h % 2 == 0) cmp = p.x() > this.p.x();
			else cmp = p.y() > this.p.y();
			if(cmp) {
				if (rb == null) {
					rb = new Node(p);
					return true;
				} else return rb.add(p, h+1);
			} else {
				if (lb == null) {
					lb = new Node(p);
					return true;
				} else return lb.add(p, h+1);
			}
		}
		public boolean contains(Point2D p){
			return contains(p, 0);
		}
		private boolean contains(Point2D p, int h){
			if (p.equals(this.p)) return true;
			boolean cmp;
			if (h % 2 == 0) cmp = p.x() > this.p.x();
			else cmp = p.y() > this.p.y();
			if(cmp && rb != null) return rb.contains(p, h+1);
			else if(lb != null) return lb.contains(p, h+1);
			else return false;
		}
		public Point2D nearest(Point2D p, Point2D champion, double mindist, int h){
			double dmin;
			if (h % 2 == 0) dmin = p.x() - this.p.x();
			else dmin = p.y() - this.p.y();
			double d = p.distanceSquaredTo(this.p);
			if (d < mindist){
				mindist = d;
				champion = this.p;
			}
			Node first, second;
			if (dmin > 0){ first = rb; second = lb; }
			else { first = lb; second = rb;}
			if (first != null) {
				champion = first.nearest(p, champion, mindist, h+1);
				mindist = p.distanceSquaredTo(champion);
			} if (second != null && mindist > dmin*dmin) {
				champion = second.nearest(p, champion, mindist, h+1);
			}
			return champion;
		}
		public Bag<Point2D> range(RectHV rng, Bag<Point2D> results , int h) {
			if (rng.contains(p)) {
				results.add(p);
			}
			if (h % 2 == 0){
				if (rb != null && rng.xmax() > p.x())
					results = rb.range(rng, results, h+1);
				if (lb != null && rng.xmin() <= p.x())
					results = lb.range(rng, results, h+1);				
			}else{
				if (rb != null && rng.ymax() > p.y())
					results = rb.range(rng, results, h+1);
				if (lb != null && rng.ymin() <= p.y())
					results = lb.range(rng, results, h+1);
			}
			return results;
		}
		public void draw(int h, double minx, double miny, double maxx, double maxy) {
			StdDraw.setPenRadius(0.01);
			StdDraw.setPenColor(StdDraw.BLACK);
			p.draw();
			StdDraw.setPenRadius();
			if (h % 2 == 1) {
				StdDraw.setPenColor(StdDraw.BLUE);
				new Point2D(minx, p.y()).drawTo(new Point2D(maxx, p.y()));
				if (rb != null) rb.draw(h+1, minx, p.y(), maxx, maxy);
				if (lb != null) lb.draw(h+1, minx, miny, maxx, p.y());
			} else {
				StdDraw.setPenColor(StdDraw.RED);
				new Point2D(p.x(), miny).drawTo(new Point2D(p.x(), maxy));				
				if (rb != null) rb.draw(h+1, p.x(), miny, maxx, maxy);
				if (lb != null) lb.draw(h+1, minx, miny, p.x(), maxy);
			}
		}
	}
	public KdTree() {
		// construct an empty set of points
	}
	public boolean isEmpty() {
		   // is the set empty? 
		return root == null;
	}
	public int size() {
		   // number of points in the set
		if (isEmpty()) return 0;
		return size;
	}
	public void insert(Point2D p) {
		// add the point to the set (if it is not already in the set)
		if (root == null) {
			root = new Node(p);
			size++;
		}
		else if (root.add(p)) size++;
	}
	public boolean contains(Point2D p) {
		   // does the set contain point p?
		if (root == null) return false; 
		return root.contains(p);
	}
	public void draw() {
		   // draw all points to standard draw
		StdDraw.setXscale();
		StdDraw.setYscale();
		if (root != null) {
			root.draw(0, 0, 0, 1, 1);
		}
		StdDraw.show();
	}
	public Iterable<Point2D> range(RectHV rect) {
		   // all points that are inside the rectangle
		Bag<Point2D> result = new Bag<Point2D>();
		if (root != null) result = root.range(rect, result, 0);
		return result;
	}
	public Point2D nearest(Point2D p) {
		   // a nearest neighbor in the set to point p; null if the set is empty 
		Point2D champion = null;
		double mindist = Double.POSITIVE_INFINITY;
		if (root == null) return null;
		else return root.nearest(p, champion, mindist, 0);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		KdTree tree = new KdTree();
		tree.insert(new Point2D(0.5,0.5));
		tree.insert(new Point2D(0.25,0.25));
		tree.insert(new Point2D(0.75,0.75));
		tree.insert(new Point2D(0.75,0.25));
		tree.draw();
	}

}
