package a3;

public class TransformedPicture implements Picture{
	final private Picture source;
	final private PixelTransformation xform;
	private int width;
	private int height;
	private Pixel[][] picture;
	  
	public TransformedPicture (Picture source, PixelTransformation xform) {
		this.source = source;
		this.xform = xform;
		this.width = source.getWidth();
		this.height = source.getHeight();
		 
		picture = new Pixel[width][height];
		for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                picture[i][j] = source.getPixel(i,j);
            }
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
		if(x<0 || y<0 || x>= width || y>=height) {
			throw new IllegalArgumentException("in valid input");
		}else {
		// transform
		return this.xform.transform(source.getPixel(x, y));
		}
	}

	@Override
	public Picture paint(int x, int y, Pixel p) {
		// TODO Auto-generated method stub
		MutablePixelArrayPicture NewPicture = new MutablePixelArrayPicture(this.picture);
		NewPicture.paint(x, y, p);
		return NewPicture;
	}

	@Override
	public Picture paint(int x, int y, Pixel p, double factor) {
		// TODO Auto-generated method stub
		MutablePixelArrayPicture NewPicture = new MutablePixelArrayPicture(this.picture);
		NewPicture.paint(x, y, p, factor);
		return NewPicture;
	}

	@Override
	public Picture paint(int ax, int ay, int bx, int by, Pixel p) {
		// TODO Auto-generated method stub
		MutablePixelArrayPicture NewPicture = new MutablePixelArrayPicture(this.picture);
		NewPicture.paint(ax, ay, bx, by, p);
		return NewPicture;
	}

	@Override
	public Picture paint(int ax, int ay, int bx, int by, Pixel p, double factor) {
		// TODO Auto-generated method stub
		MutablePixelArrayPicture NewPicture = new MutablePixelArrayPicture(this.picture);
		NewPicture.paint(ax, ay, bx, by, p, factor);
		return NewPicture;
	}

	@Override
	public Picture paint(int cx, int cy, double radius, Pixel p) {
		// TODO Auto-generated method stub
		MutablePixelArrayPicture NewPicture = new MutablePixelArrayPicture(this.picture);
		NewPicture.paint(cx, cy, radius, p);
		return NewPicture;
	}

	@Override
	public Picture paint(int cx, int cy, double radius, Pixel p, double factor) {
		// TODO Auto-generated method stub
		MutablePixelArrayPicture NewPicture = new MutablePixelArrayPicture(this.picture);
		NewPicture.paint(cx, cy, radius, p, factor);
		return NewPicture;
	}

}
