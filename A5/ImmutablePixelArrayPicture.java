package a5;

import java.util.Iterator;

//class of immutable pixel array picture
public class ImmutablePixelArrayPicture extends PixelArrayPicture implements Picture {
	
	// constructor calling parent constructor
	public ImmutablePixelArrayPicture(Pixel[][] parray, String caption) {
		super(parray, caption);
	}

	@Override
	/*
	 * input: position at (x,y); a pixel; factor
	 * output: an immutable picture with pixel painted
	 */
	public Picture paint(int x, int y, Pixel p, double factor) {
		/*
		 * check parameters: x and y should not be out of the boundary
		 * pixel should not be null
		 * factor should not be out of the range
		 */
		if(p == null) {
			throw new IllegalArgumentException("Picture is null");
		}
		if((factor < 0.0D) || (factor > 1.0D)) {
			throw new IllegalArgumentException("Factor is out of range");
		}
		if ((x < 0) || (x >= getWidth()) || (y < 0) || (y >= getHeight())) {
			throw new IllegalArgumentException("X or Y is out of range");
		}
		
		return new MutablePixelArrayPicture(pixelarray, getCaption()).paint(x, y, p, factor);
	}
	
	/*
	 * input: boundaries(ax,ay,bx,by); a pixel; factor
	 * output: an immutable picture with pixel painted
	 */
	public Picture paint(int ax, int ay, int bx, int by, Pixel p, double factor) {
		return copyImmutable(super.paint(ax, ay, bx, by, p, factor));
	}
	
	/*
	 * input: center(cx,cy); radius; a pixel; factor
	 * output: an immutable picture with pixel painted
	 */
	public Picture paint(int cx, int cy, double radius, Pixel p, double factor) {
		return copyImmutable(super.paint(cx, cy, radius, p, factor));
	}

	/*
	 * input: position at (x,y); a picture; factor
	 * output: an immutable picture with pixel painted
	 */
	public Picture paint(int x, int y, Picture p, double factor) {
		return copyImmutable(super.paint(x, y, p, factor));
	}

	// create a copy of the immutable picture
	private static Picture copyImmutable(Picture p) {
		if (p == null) {
			throw new IllegalArgumentException("Picture p is null");
		}
		Pixel[][] parray = new Pixel[p.getWidth()][p.getHeight()];
		for (int x = 0; x < p.getWidth(); x++) {
			for (int y = 0; y < p.getHeight(); y++) {
				parray[x][y] = p.getPixel(x, y);
			}
		}
		return new ImmutablePixelArrayPicture(parray, p.getCaption());
	}
	
}
