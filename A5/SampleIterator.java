package a5;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class SampleIterator implements Iterator<Pixel> {
	private Picture source;
    private int ix;
    private int iy;
    private int dx;
    private int dy;

    private int x;
    private int y;
    
	public SampleIterator(Picture p, int ix, int iy, int dx, int dy) {
		if (p == null) {
            throw new IllegalArgumentException("Attempted to create a sample iterator for a null picture.");
        }
        if (ix < 0 || ix >= p.getWidth()) {
            throw new IllegalArgumentException("X coordinate of the initial position is illegal.");
        }
        if (iy < 0 || iy >= p.getHeight()) {
            throw new IllegalArgumentException("Y coordinate of the initial position is illegal.");
        }
        if (dx <= 0) {
            throw new IllegalArgumentException("Cannot have non-positive dx value.");
        }
        if (dy <= 0) {
            throw new IllegalArgumentException("Cannot have non-positive dy value.");
        }
        
        this.source = p;
        this.ix = ix;
        this.iy = iy;
        this.dx = dx;
        this.dy = dy;
        
        x = ix - dx;
        y = iy;
        
	}
	
	@Override
	public boolean hasNext() {
		// TODO Auto-generated method stub
		return goDown() || goRight();
	}

	/*
	 * first go right by dx each time
	 * if cannot go right, go down instead
	 * end when cannot go down or go right
	 */
	@Override
	public Pixel next() {
		// TODO Auto-generated method stub
		 if (!hasNext()) {
			 throw new NoSuchElementException();
	     }
	     if (goRight()) {
	            x += dx; 
	     }else {
	            y += dy;
	            x = ix;
	     }
	     return source.getPixel(x, y);
	}
	
	 private boolean goRight() {
		 return x + dx < source.getWidth();
	 }
	    
	 private boolean goDown() {
		 return y + dy < source.getHeight();
	 }
}
