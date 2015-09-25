import java.util.Arrays;

import edu.princeton.cs.algs4.ResizingArrayBag;

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
		Point[] sortedp = new Point[points.length];
		Point p;
		for (int i = 0; i < points.length; i++) {
			Point pi = points[i];
			for (int j = 0; j< points.length; j++) {
				sortedp[j] = points[j];
			}
			sortedp[i] = sortedp[0];
			sortedp[0] = pi;
			segment = new ResizingArrayBag<Point>();
			segment.add(pi);
			Arrays.sort(sortedp, 1, points.length, pi.slopeOrder);
			p = sortedp[1];
			segment.add(p);
			for (int j = 2; j < sortedp.length; j++) {
				Point pj = sortedp[j];
				if (pj != pi) {
					if (pi.slopeOrder.compare(p, pj) != 0) {
						// New slope, save old segment and start a new one.
						addSegment(segment);
						segment = new ResizingArrayBag<Point>();
						segment.add(pi);
						p = pj;
					}
					segment.add(pj);
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
