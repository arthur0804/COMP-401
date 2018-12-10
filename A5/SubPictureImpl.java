package a5;

import java.util.Iterator;

//class of sub picture
public class SubPictureImpl extends PictureImpl implements SubPicture {
	private Picture src; // source picture
	private int xoff; // x offset
	private int yoff; // y offset
	private int width; // width of sub picture
	private int height; // height of sub picture
	private String caption; // caption of sub picture

	// constructor
	public SubPictureImpl(Picture src, int xoff, int yoff, int width, int height) {
		/*
		 * call parent constructor
		 * check parameters: should not be out of boundaries of the source picture
		 */
		super(checkSourcePicture(src).getCaption());
		if(src == null) {
			throw new IllegalArgumentException("Source picture is null");
		}
		this.src = src;
		if ((xoff < 0) || (xoff >= src.getWidth()) || 
				(yoff < 0) || (yoff >= src.getHeight()) || 
				(width < 1) || (xoff + width > src.getWidth()) || 
				(height < 1) || (yoff + height > src.getHeight())) {
			throw new IllegalArgumentException("Illegal geometry parameters");
		}
		this.xoff = xoff;
		this.yoff = yoff;
		this.width = width;
		this.height = height;
		this.caption = src.getCaption();
	}
	
	private static Picture checkSourcePicture(Picture src) {
		if(src == null) {
			throw new IllegalArgumentException("Source picture is null");
		}
		return src;
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
		/* get the pixel at position (x,y)
		 * x and y should not be out of boundaries
		 */
		if((x < 0) || (x >= getWidth()) || (y < 0) || (y >= getHeight())) {
			throw new IllegalArgumentException("X or Y is out of range");
		}
		return src.getPixel(x + xoff, y + yoff);
	}

	@Override
	/*
	 * input: position at (x,y); a picture; factor
	 * if the result is the same, the output is the sub picture itself
	 * otherwise the output is a new sub picture 
	 */
	public Picture paint(int x, int y, Pixel p, double factor) {
		// check parameters
		if(p == null) {
			throw new IllegalArgumentException("Picture is null");
		}
		if((factor < 0.0D) || (factor > 1.0D)) {
			throw new IllegalArgumentException("Factor is out of range");
		}
		if ((x < 0) || (x >= getWidth()) || (y < 0) || (y >= getHeight())) {
			throw new IllegalArgumentException("X or Y is out of range");
		}
		
		Picture ResultPicture = src.paint(x+xoff, y+yoff, p, factor);
		
		if (ResultPicture == src) {
			src = ResultPicture;
			return this;
		}
		SubPicture new_subpicture = ResultPicture.extract(getXOffset(), getYOffset(), getWidth(), getHeight());
		new_subpicture.setCaption(getCaption());
		return new_subpicture;
	}

	@Override
	public int getXOffset() {
		// TODO Auto-generated method stub
		return this.xoff;
	}

	@Override
	public int getYOffset() {
		// TODO Auto-generated method stub
		return this.yoff;
	}

	@Override
	public Picture getSource() {
		// TODO Auto-generated method stub
		return this.src;
	}

}
