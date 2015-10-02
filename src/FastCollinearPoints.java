import java.util.Arrays;

import edu.princeton.cs.algs4.ResizingArrayBag;

public class FastCollinearPoints {
	private Point[] points;
	private int minlength;
	private ResizingArrayBag<Point[]> segments;
	private LineSegment[] result = new LineSegment[0];
	
	public FastCollinearPoints(Point[] points) {
		// finds all line segments containing 4 or more points
		minlength = 4;
		if (points == null) throw new NullPointerException();
		this.points = Arrays.copyOf(points, points.length);
		checkinput();
		findSegments();
	}
	private void checkinput() {
		if (points.length == 0) return;
		Arrays.sort(this.points);
		for (int i = 0; i < this.points.length; i++) {
			if (this.points[i] == null) throw new NullPointerException();
			if (i > 0 && this.points[i].compareTo(this.points[i-1]) == 0) throw new IllegalArgumentException("Duplicate point found");
		}
	}
	private void findSegments() {
		if (points.length < minlength) return;
		segments = new  ResizingArrayBag<Point[]>();
		ResizingArrayBag<Point> segment;
		Point[] sp = Arrays.copyOf(points, points.length);
		int next_index = 1;
		Point next;
		for (int i = 0; i < points.length-minlength+1; i++) {
			Point pi = points[i];
			if (i > 0) {
				sp[next_index] = sp[0];
				sp[0] = pi;
			}
			segment = new ResizingArrayBag<Point>();
			segment.add(pi);
			Arrays.sort(sp, 1, points.length, pi.slopeOrder());
			segment.add(sp[1]);
			next = points[i+1];
			next_index = 1;
			for (int j = 2; j < sp.length; j++) {
				if (next == sp[j]) next_index = j;
				if (pi.slopeOrder().compare(sp[j-1], sp[j]) != 0) {
					// New slope, save old segment and start a new one.
					addSegment(segment);
					segment = new ResizingArrayBag<Point>();
					segment.add(pi);
				}
				segment.add(sp[j]);
			}
			addSegment(segment);
		}
		result = new LineSegment[segments.size()];
		int i = 0;
		for (Point[] ls: segments) {
			result[i++] = new LineSegment(ls[0], ls[1]);
		}
		segments = null;
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
		return result.length;
	}
	public LineSegment[] segments() {
		// the line segments
		return Arrays.copyOf(result, result.length);

	}
}
