import java.util.Arrays;

import edu.princeton.cs.algs4.ResizingArrayBag;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {
	private Point[] points;
	private int minlength;
	private ResizingArrayBag<Point[]> segments;
	public FastCollinearPoints(Point[] points) {
		// finds all line segments containing 4 or more points
		minlength = 4;
		if (points == null) throw new NullPointerException();
		loadPoints(points);
		findSegments();
	}
	private void loadPoints(Point[] points) {
		int len = points.length;
		this.points = new Point[len];
		for (int i = 0; i < len; i++) {
			if (points[i] == null) throw new NullPointerException();
			this.points[i] = points[i];
		}
	}
	private void findSegments() {
		segments = new  ResizingArrayBag<Point[]>();
		ResizingArrayBag<Point> segment;
		Point[] sortedp = Arrays.copyOf(points, points.length);
		Point p;
		for (int i = 0; i < points.length; i++) {
			Point pi = points[i];
			StdOut.println(pi);
			segment = new ResizingArrayBag<Point>();
			segment.add(pi);
			Arrays.sort(sortedp, pi.slopeOrder);
			for (Point p2: sortedp){
				StdOut.printf(" %s",p2);
			}
			StdOut.println();
			p = sortedp[0];
			for (int j = 0; j < sortedp.length; j++) {
				if (pi.slopeOrder.compare(p, sortedp[j]) != 0) {
					// New slope, save old segment and start a new one.
					addSegment(segment);
					segment = new ResizingArrayBag<Point>();
					segment.add(pi);
					p = sortedp[j];
				}
				if (sortedp[j] != pi) {
					segment.add(sortedp[j]);
				}
			}
			addSegment(segment);
		}
	}
	private void addSegment(ResizingArrayBag<Point> segment) {
		if (segment.size() < minlength) return;
		Point[] ps = new Point[segment.size()];
		int i = 0;
		for (Point p: segment) {
			ps[i++] = p;
		}
		Arrays.sort(ps);
		Point[] result = {ps[0], ps[i-1]};
		for (Point[] s: segments) {
			if (s[0] == result[0] && s[1] == result[1]) return;
		}
		StdOut.printf("Adding %s -> %s\n",result[0],result[1]);
		segments.add(result);
	}
	public int numberOfSegments() {
		// the number of line segments
		return segments.size();
	}
	public LineSegment[] segments() {
		// the line segments
		
		LineSegment[] result = new LineSegment[numberOfSegments()];
		int i = 0;
		for (Point[] ls: segments) {
			result[i++] = new LineSegment(ls[0],ls[1]);
		}
		return result;

	}
}
