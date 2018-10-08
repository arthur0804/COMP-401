package a3;

public class MonochromePicture implements Picture {
 
	final private int width;  
	final private int height;
	final private Pixel value;
	 
	// constructor 
	public MonochromePicture(int width, int height, Pixel value) {
		if(width <= 0 || height <= 0 || value == null) {
			throw new IllegalArgumentException("in valid input");
		}else {
			this.width = width;
			this.height = height;
			this.value = value;
		}
	}
	
	@Override
	public int getWidth() {
		// TODO Auto-generated method stub
		return this.width;
	}

	@Override
	public int getHeight() {
		// TODO Auto-generated method stub
		return this.height;
	}

	@Override
	public Pixel getPixel(int x, int y) {
		// TODO Auto-generated method stub
		if(x<0 || y<0 || x >= width || y>=height) {
			throw new IllegalArgumentException("in valid input");
		}else {
			return this.value;
		}
	}

	@Override
	public Picture paint(int x, int y, Pixel p) {
		// TODO Auto-generated method stub
		if (p == this.value) {
			// no change
			return this;
		}else {
			// if changed, it is not more a monochrome picture, so create a new object
			MutablePixelArrayPicture NewPicture = new MutablePixelArrayPicture(this.width, this.height, this.value);	
			// paint on the new picture
			NewPicture.paint(x, y, p);
			return NewPicture;
		}
	}

	@Override
	public Picture paint(int x, int y, Pixel p, double factor) {
		// TODO Auto-generated method stub
		if (p == this.value) {
			// no change
			return this;
		}else {
			// if changed, it is not more a monochrome picture, so create a new object
			MutablePixelArrayPicture NewPicture = new MutablePixelArrayPicture(this.width, this.height, this.value);	
			// paint on the new picture
			NewPicture.paint(x, y, p, factor);
			return NewPicture;
		}
	}

	@Override
	public Picture paint(int ax, int ay, int bx, int by, Pixel p) {
		// TODO Auto-generated method stub
		if (p == this.value) {
			// no change
			return this;
		}else {
			// if changed, it is not more a monochrome picture, so create a new object
			MutablePixelArrayPicture NewPicture = new MutablePixelArrayPicture(this.width, this.height, this.value);
			NewPicture.paint(ax, ay, bx, by, p);
			return NewPicture;
		}
	}

	@Override
	public Picture paint(int ax, int ay, int bx, int by, Pixel p, double factor) {
		if (p == this.value) {
			// no change
			return this;
		}else {
			// if changed, it is not more a monochrome picture, so create a new object
			MutablePixelArrayPicture NewPicture = new MutablePixelArrayPicture(this.width, this.height, this.value);
			NewPicture.paint(ax, ay, bx, by, p,factor);
			return NewPicture;
		}
	}

	@Override
	public Picture paint(int cx, int cy, double radius, Pixel p) {
		if (p == this.value) {
			// no change
			return this;
		}else {
			// if changed, it is not more a monochrome picture, so create a new object
			MutablePixelArrayPicture NewPicture = new MutablePixelArrayPicture(this.width, this.height, this.value);
			NewPicture.paint(cx, cy, radius, p);
			return NewPicture;
		}
	}

	@Override
	public Picture paint(int cx, int cy, double radius, Pixel p, double factor) {
		if (p == this.value) {
			// no change
			return this;
		}else {
			// if changed, it is not more a monochrome picture, so create a new object
			MutablePixelArrayPicture NewPicture = new MutablePixelArrayPicture(this.width, this.height, this.value);
			NewPicture.paint(cx, cy, radius, p, factor);
			return NewPicture;
		}
	}

}
