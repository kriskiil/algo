import java.util.Arrays;

import edu.princeton.cs.algs4.SET;

public class FastCollinearPoints {
	private Point[] points;
	private int minlength;
	private SET<Segment> segments;
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
		segments = new  SET<Segment>();
		Segment segment;
		Point[] sp = Arrays.copyOf(points, points.length);
		int next_index = 1;
		Point next;
		for (int i = 0; i < points.length-minlength+1; i++) {
			Point pi = points[i];
			if (i > 0) {
				sp[next_index] = sp[0];
				sp[0] = pi;
			}
			segment = new Segment(pi);
			Arrays.sort(sp, 1, points.length, pi.slopeOrder());
			segment.add(sp[1]);
			next = points[i+1];
			next_index = 1;
			for (int j = 2; j < sp.length; j++) {
				if (next == sp[j]) next_index = j;
				if (pi.slopeOrder().compare(sp[j-1], sp[j]) != 0) {
					// New slope, save old segment and start a new one.
					if (segment.size()>=minlength)
						segments.add(segment);
					segment = new Segment(pi);
				}
				segment.add(sp[j]);
			}
			if (segment.size() >= minlength)
				segments.add(segment);
		}
		result = new LineSegment[segments.size()];
		int i = 0;
		for (Segment ls: segments) {
			result[i++] = new LineSegment(ls.min, ls.max);
		}
		segments = null;
	}
	private class Segment implements Comparable<Segment>{
		Point min;
		Point max;
		private int size = 0;
		public Segment(Point initial){
			this.min = initial;
			this.max = initial;
			size++;
		}
		public int compareTo(Segment that){
			int cmpmin = this.min.compareTo(that.min);
			int cmpmax = this.max.compareTo(that.max);
			if (cmpmin == 0) {
				return cmpmax;
			}else{
				return cmpmin;
			}
		}
		public void add(Point p){
			if (p.compareTo(min)<0) this.min = p;
			if (p.compareTo(max)>0) this.max = p;
			size++;
		}
		public int size(){
			return size;
		}
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
