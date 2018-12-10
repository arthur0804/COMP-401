package a5;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class WindowIterator implements Iterator<SubPicture> {
	private Picture source;
    private int width;
    private int height;

    private int x;
    private int y;
    
	public WindowIterator(Picture p, int w, int h) {
		if (p == null) {
            throw new IllegalArgumentException("Attempted to create a WindowIterator for a null picture.");
        }
        if (w <= 0 || w > p.getWidth()) {
            throw new IllegalArgumentException("Illegal window width.");
        }
        if (h <= 0 || h > p.getHeight()) {
            throw new IllegalArgumentException("Illegal window height.");
        }
        this.source = p;
        this.width = w;
        this.height = h;

        x = -1;
        y = 0;
	}
	
	@Override
	public boolean hasNext() {
		// TODO Auto-generated method stub
		return goRight() || goDown();
	}
	
	/*
	 * first go right by 1 each time
	 * if cannot go right, go down instead
	 * end when cannot go down or go right
	 */
	@Override
	public SubPicture next() {
		if (!hasNext()) {
	        throw new NoSuchElementException();
	    }
	    if (goRight()) {
	        x++;
	     }else {
	        y++;
	        x = 0;
	     }
	     return source.extract(x, y, width, height);
	}
	
	private boolean goRight() {
        return x + width < source.getWidth();
    }
	
    private boolean goDown() {
        return y + height < source.getHeight();
    }
}
