import java.util.Arrays;

import edu.princeton.cs.algs4.ResizingArrayBag;

public class FastCollinearPoints {
	private Point[] points;
	private ResizingArrayBag<LineSegment> segments;
	public FastCollinearPoints(Point[] points) {
		// finds all line segments containing 4 or more points
		if (points == null) throw new NullPointerException();
		int len = points.length;
		this.points = new Point[len];
		for (int i = 0; i < len; i++) {
			if (points[i] == null) throw new NullPointerException();
			this.points[i] = points[i];
		}
		segments = new  ResizingArrayBag<LineSegment>();
		ResizingArrayBag<Point> segment = new ResizingArrayBag<Point>();
		int first = 0;
		Point p = this.points[first];
		for (int i = 0; i < len-1; i++) {
			Point pi = this.points[i];
			segment.add(pi);
			Arrays.sort(this.points,i+1,len-1,pi.slopeOrder);
			for (int j = i+1; j < len; j++) {
				if (pi.slopeOrder.compare(p,this.points[j]) == 0) {
					segment.add(this.points[j]);
				} else {
					if (segment.size() > 3) {
						addSegment(segment);
						for (int k = first; k < j; k++) {
							Point tmp = this.points[++i];
							this.points[i] = this.points[k];
							this.points[k] = tmp;
						}
					}
					segment = new ResizingArrayBag<Point>();
					segment.add(pi);
					first = j;
					p = this.points[j];
				}
			}
			if (segment.size() > 3) {
				addSegment(segment);
				for (int k = first; k < len-1; k++) {
					Point tmp = this.points[++i];
					this.points[i] = this.points[k];
					this.points[k] = tmp;
				}
				segment = new ResizingArrayBag<Point>();
			}
		}
	}
	private void addSegment(ResizingArrayBag<Point> segment) {
		Point[] ps = new Point[segment.size()];
		int i = 0;
		for (Point p: segment) {
			ps[i++] = p;
		}
		Arrays.sort(ps);
		segments.add(new LineSegment(ps[0],ps[segment.size()-1]));
	}
	public int numberOfSegments() {
		// the number of line segments
		return segments.size();
	}
	public LineSegment[] segments() {
		// the line segments
		LineSegment[] result = new LineSegment[numberOfSegments()];
		int i = 0;
		for (LineSegment ls: segments) {
			result[i++] = ls;
		}
		return result;

	}
}
