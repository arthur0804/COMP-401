package a5;

import java.util.Iterator;

// class of immutable pixel array picture
public class MutablePixelArrayPicture extends PixelArrayPicture implements Picture {
	
	// constructor calling parent constructor
	public MutablePixelArrayPicture(Pixel[][] parray, String caption) {
		super(parray, caption);
	}

	@Override
	/*
	 * input: position at (x,y); a pixel; factor
	 * output: a mutable picture with pixel painted
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
		
		pixelarray[x][y] = pixelarray[x][y].blend(p, factor);
		return this;
	}
}
