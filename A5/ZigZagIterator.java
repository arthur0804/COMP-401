package a5;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ZigZagIterator implements Iterator<Pixel> {
	private Picture source;
    private int x;
    private int y;
    
	public ZigZagIterator(Picture p) {
		 if (p == null) {
	            throw new IllegalArgumentException("a null picture.");
	        }
	        this.source = p;
	        x = -1;
	        y = 1;
	}
	
	 @Override
	 public boolean hasNext() {
		 return inSameLine() || hasNextLine();
	 }

	 @Override
	 public Pixel next() {
		 if (!hasNext()) {
			 throw new NoSuchElementException("No more pixels in the picture.");
	     }
	     if (inSameLine()) {
	    	 if (isEven()) {
	    		 x++;
	             y--;
	         } else {
	             x--;
	             y++;
	         }
	     } else {
	    	 if (isEven()) {
	    		 if (x == source.getWidth() - 1) {
	    			 y++;
	    		 } else {
	    			 x++;
	    		 }
	    	 } else {
	    		 if (y == source.getHeight() - 1) {
	    			 x++;
	    		 } else {
	    			 y++;
	    		 }
	    	 }
	     }
	     return source.getPixel(x, y);
	 }

	 private boolean inSameLine() {
		 if (isEven()) {
			 return pixelInside(x + 1, y - 1);
		 } else {
			 return pixelInside(x - 1, y + 1);
		 }
	}
	    
	 /*
	  * check whether there is next diagonal 
	  */
	 private boolean hasNextLine() {
		return x + y + 1 < source.getWidth() + source.getHeight() - 1;
	 }

	private boolean isEven() {
		return (x + y) % 2 == 0;
	}

	/*
	 * check whether pixel at (x,y) is inside the picture
	 */
	private boolean pixelInside(int x, int y) {
		return x >= 0 && x < source.getWidth() && y >= 0 && y < source.getHeight();
	}

}
