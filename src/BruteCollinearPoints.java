import java.util.Arrays;

import edu.princeton.cs.algs4.ResizingArrayBag;
public class BruteCollinearPoints {
	private Point[] points;
	private ResizingArrayBag<LineSegment> l4segments;
	public BruteCollinearPoints(Point[] points) {
		// finds all line segments containing 4 points
		if (points == null) throw new NullPointerException();
		int len = points.length;
		// Copy array to local
		this.points = Arrays.copyOf(points, points.length);
		Arrays.sort(this.points);
		for (int i = 0; i < len; i++) {
			if (points[i] == null) throw new NullPointerException();
			if (i > 0 && points[i].compareTo(points[i-1]) == 0) throw new IllegalArgumentException("Duplicate point found");
		}
		l4segments = new ResizingArrayBag<LineSegment>();
		Point[] segment = new Point[4];		
		for (int i = 0; i < len; i++) {
			Point pi = this.points[i];
			for (int j = i+1; j < len; j++) {
				double sj = pi.slopeTo(this.points[j]);
				for (int k = j+1; k < len; k++) {
					if (pi.slopeTo(this.points[k]) == sj) {
						for (int l = k+1; l < len; l++) {
							if (pi.slopeTo(this.points[l]) == sj) {
								segment[0] = pi;
								segment[1] = this.points[j];
								segment[2] = this.points[k];
								segment[3] = this.points[l];
								Arrays.sort(segment);
								this.l4segments.add(new LineSegment(segment[0],segment[3]));
							}
						}
					}
				}
			}
		}
	}
	public int numberOfSegments() {
		// the number of line segments
		return l4segments.size();
	}
	public LineSegment[] segments() {
		// the line segments
		LineSegment[] result = new LineSegment[numberOfSegments()];
		int i = 0;
		for (LineSegment ls: l4segments) {
			result[i++] = ls;
		}
		return result;
	}
}
