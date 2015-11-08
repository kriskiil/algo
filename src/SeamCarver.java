import java.awt.Color;

import edu.princeton.cs.algs4.Picture;


public class SeamCarver {
	Picture picture;
	public SeamCarver(Picture picture) {
		// create a seam carver object based on the given picture
		this.picture = new Picture(picture);
	}
	public Picture picture() {
		// current picture
		return picture;
	}
	public int width() {
		// width of current picture
		return picture.width();
	}
	public int height() {
		// height of current picture
		return picture.height();
	}
	public double energy(int x, int y) {
		// energy of pixel at column x and row y
		Color up = picture.get(x, y-1);
		Color down = picture.get(x, y+1);
		Color left = picture.get(x-1, y);
		Color right = picture.get(x+1, y);
		if (x == 0 || y == 0 || x == picture.height()-1 || y == picture.height()-1) {
			return Math.pow(1000, 2);
		}
		double dx = 	(left.getRed()-right.getRed())<<2 +
				(left.getGreen()-right.getGreen())<<2 +
				(left.getBlue()-right.getBlue())<<2;
		double dy = 	(up.getRed()-down.getRed())<<2 +
				(up.getGreen()-down.getGreen())<<2 +
				(up.getBlue()-down.getBlue())<<2;
		return dx + dy;
	}
	public int[] findHorizontalSeam() {
		// sequence of indices for horizontal seam
		int [] seam = new int[picture.width()];
	}
	public int[] findVerticalSeam() {
		// sequence of indices for vertical seam
		int [] seam = new int[picture.height()];
	}
	public void removeHorizontalSeam(int[] seam) {
		// remove horizontal seam from current picture
	}
	public void removeVerticalSeam(int[] seam) {
		// remove vertical seam from current picture
	}


	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
