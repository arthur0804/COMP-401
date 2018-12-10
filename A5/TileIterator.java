package a5;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class TileIterator implements Iterator<SubPicture> {
	private Picture source;
    private int width;
    private int height;

    private int x;
    private int y;
    
	public TileIterator(Picture p, int w, int h) {
		 if (p == null) {
	            throw new IllegalArgumentException("Attempted to create a TileIterator for a null picture.");
	        }
	        if (w <= 0 || w > p.getWidth()) {
	            throw new IllegalArgumentException("Illegal tile width.");
	        }
	        if (h <= 0 || h > p.getHeight()) {
	            throw new IllegalArgumentException("Illegal tile height.");
	        }
	        source = p;
	        width = w;
	        height = h;

	        x = 0 - width;
	        y = 0;
	}
	
	@Override
	public boolean hasNext() {
		// TODO Auto-generated method stub
		return goDown() || goRight();
	}
	
	/*
	 * first go right by width each time
	 * if cannot go right, go down instead
	 * end when cannot go down or go right
	 */
	@Override
	public SubPicture next() {
		// TODO Auto-generated method stub
		if(!hasNext()) {
			throw new NoSuchElementException();
		}
		if(goRight()) {
			 x += width;
		}else{
			 y += height;
	         x = 0;
		}
		return source.extract(x, y, width, height);
	}
	
	 private boolean goRight() {
		 return x + width + width - 1 < source.getWidth();
	 }
	 private boolean goDown() {
		 return y + height + height - 1 < source.getHeight();
	 }
}
