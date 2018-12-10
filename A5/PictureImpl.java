package a5;

import java.util.Iterator;

//class of picture, parent class of pixel array picture
public abstract class PictureImpl implements Picture{

	private String caption;
	
	// constructor
	public PictureImpl (String caption) {
		// caption should not be null
		if(caption == null) {
			throw new IllegalArgumentException("Caption cannot be null!");
		}else {
			this.caption = caption;
		}
	}
	
	@Override
	public String getCaption() {
		// get caption
		return this.caption;
	}

	@Override
	public void setCaption(String caption) {
		// set caption before checking
		if(caption == null) {
			throw new IllegalArgumentException("Caption cannot be null!");
		}else {
			this.caption = caption;
		}
	}

	@Override
	public Picture paint(int x, int y, Pixel p) {
		// TODO Auto-generated method stub
		return paint(x, y, p, 1.0D); 
	}

	@Override
	public Picture paint(int ax, int ay, int bx, int by, Pixel p) {
		// TODO Auto-generated method stub
		return paint(ax, ay, bx, by, p, 1.0D); 
	}

	@Override
	public Picture paint(int ax, int ay, int bx, int by, Pixel p, double factor) {
		// TODO Auto-generated method stub
		if (p == null) {
			throw new IllegalArgumentException("Pixel p is null");
		}
		if ((factor < 0.0D) || (factor > 1.0D)) {
			throw new IllegalArgumentException("factor out of range");
		}
		int min_x = ax < bx ? ax : bx;
		int max_x = ax > bx ? ax : bx;
		int min_y = ay < by ? ay : by;
		int max_y = ay > by ? ay : by;

		min_x = min_x < 0 ? 0 : min_x;
		min_y = min_y < 0 ? 0 : min_y;
		max_x = max_x > getWidth() - 1 ? getWidth() - 1 : max_x;
		max_y = max_y > getHeight() - 1 ? getHeight() - 1 : max_y;

		Picture result = this;
		for (int x = min_x; x <= max_x; x++) {
			for (int y = min_y; y <= max_y; y++) {
				result = result.paint(x, y, p, factor);
			}
		}
		return result;
	}

	@Override
	public Picture paint(int cx, int cy, double radius, Pixel p) {
		// TODO Auto-generated method stub
		return paint(cx, cy, radius, p, 1.0D);
	}

	@Override
	public Picture paint(int cx, int cy, double radius, Pixel p, double factor) {
		if (p == null) {
			throw new IllegalArgumentException("Pixel p is null");
		}
		if ((factor < 0.0D) || (factor > 1.0D)) {
			throw new IllegalArgumentException("factor out of range");
		}
		if (radius < 0.0D) {
			throw new IllegalArgumentException("radius is negative");
		}
		int min_x = (int) (cx - (radius + 1.0D));
		int max_x = (int) (cx + (radius + 1.0D));
		int min_y = (int) (cy - (radius + 1.0D));
		int max_y = (int) (cy + (radius + 1.0D));

		min_x = min_x < 0 ? 0 : min_x;
		min_y = min_y < 0 ? 0 : min_y;
		max_x = max_x > getWidth() - 1 ? getWidth() - 1 : max_x;
		max_y = max_y > getHeight() - 1 ? getHeight() - 1 : max_y;

		Picture result = this;
		for (int x = min_x; x <= max_x; x++) {
			for (int y = min_y; y <= max_y; y++) {
				if (Math.sqrt((cx - x) * (cx - x) + (cy - y) * (cy - y)) <= radius) {
					result = result.paint(x, y, p, factor);
				}
			}
		}
		return result;
	}

	@Override
	public Picture paint(int x, int y, Picture p) {
		// TODO Auto-generated method stub
		return paint(x, y, p, 1.0D);
	}

	@Override
	public Picture paint(int x, int y, Picture p, double factor) {
		// TODO Auto-generated method stub
		if(p == null) {
			throw new IllegalArgumentException("Picture is null");
		}
		if((factor < 0.0D) || (factor > 1.0D)) {
			throw new IllegalArgumentException("Factor is out of range");
		}
		if ((x < 0) || (x >= getWidth()) || (y < 0) || (y >= getHeight())) {
			throw new IllegalArgumentException("X or Y is out of range");
		}
		
		Picture result = this;
		for(int px = 0; (px < p.getWidth()) && (px+x < this.getWidth()); px++ ) {
			for(int py = 0; (py < p.getHeight() && py+y < this.getHeight()); py++) {
				result = result.paint(px+x, py+y, p.getPixel(px, py), factor);
			}
		}
		return result;
	}

	@Override
	public SubPicture extract(int x, int y, int width, int height) {
		// TODO Auto-generated method stub
		return new SubPictureImpl(this, x , y, width, height);
	}

	@Override
	public Iterator<Pixel> sample(int init_x, int init_y, int dx, int dy) {
		// TODO Auto-generated method stub
		return new SampleIterator(this, init_x, init_y, dx, dy);
	}

	@Override
	public Iterator<SubPicture> window(int window_width, int window_height) {
		// TODO Auto-generated method stub
		return new WindowIterator(this, window_width, window_height);
	}

	@Override
	public Iterator<SubPicture> tile(int tile_width, int tile_height) {
		// TODO Auto-generated method stub
		return new TileIterator(this, tile_width, tile_height);
	}

	@Override
	public Iterator<Pixel> zigzag() {
		// TODO Auto-generated method stub
		return new ZigZagIterator(this);
	}
}
