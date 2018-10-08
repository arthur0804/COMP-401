package a3;
 
public class GradientPicture implements Picture{
	   
	final private int width;
	final private int height;
	final private Pixel upper_left;
	final private Pixel upper_right;
	final private Pixel lower_left;
	final private Pixel lower_right;
	
	// constructor
	public GradientPicture(int width, int height, Pixel upper_left, Pixel upper_right, 
			Pixel lower_left, Pixel lower_right) {
		if(width <= 0 || height <= 0 || upper_left == null || upper_right == null || lower_left == null || lower_right == null) {
			throw new IllegalArgumentException("in valid input");
		}else {
			this.width = width;
			this.height = height;
			this.upper_left = upper_left;
			this.upper_right = upper_right;
			this.lower_left = lower_left;
			this.lower_right = lower_right;
		}
	}
	
	public Pixel[][] paintPictureArray(int width, int height, Pixel upper_left, Pixel upper_right, 
			Pixel lower_left, Pixel lower_right) {
		Pixel[][] pixel_array = new Pixel[width][height];
		// paint the pixel array
		for(int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				double factor_1 = (1.0 / (height-1.0)) * y;
				Pixel start_of_row = upper_left.blend(lower_left, factor_1);
				Pixel end_of_row = upper_right.blend(lower_right, factor_1);	
				double factor_2 = (1.0 / (width-1.0)) * x;
				pixel_array[x][y] = start_of_row.blend(end_of_row, factor_2);
			}
		}
		return pixel_array;
	}
	
	
	public Picture paintPicture(Pixel[][] pixel_array) {
		// create a new Picture object
		MutablePixelArrayPicture NewPicture = new MutablePixelArrayPicture(pixel_array);		
		return NewPicture;
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
		return this.paintPicture(this.paintPictureArray(width, height, upper_left, upper_right, lower_left, lower_right)).getPixel(x, y);
	}

	@Override
	public Picture paint(int x, int y, Pixel p) {
		// TODO Auto-generated method stub
		return this.paintPicture(this.paintPictureArray(width, height, upper_left, upper_right, lower_left, lower_right)).paint(x, y, p);
	}

	@Override
	public Picture paint(int x, int y, Pixel p, double factor) {
		// TODO Auto-generated method stub
		return this.paintPicture(this.paintPictureArray(width, height, upper_left, upper_right, lower_left, lower_right)).paint(x, y, p, factor);
	}

	@Override
	public Picture paint(int ax, int ay, int bx, int by, Pixel p) {
		// TODO Auto-generated method stub
		return this.paintPicture(this.paintPictureArray(width, height, upper_left, upper_right, lower_left, lower_right)).paint(ax,ay,bx,by,p);
	}

	@Override
	public Picture paint(int ax, int ay, int bx, int by, Pixel p, double factor) {
		// TODO Auto-generated method stub
		return this.paintPicture(this.paintPictureArray(width, height, upper_left, upper_right, lower_left, lower_right)).paint(ax,ay,bx,by,p,factor);
	}

	@Override
	public Picture paint(int cx, int cy, double radius, Pixel p) {
		// TODO Auto-generated method stub
		return this.paintPicture(this.paintPictureArray(width, height, upper_left, upper_right, lower_left, lower_right)).paint(cx, cy, radius, p);
	}

	@Override
	public Picture paint(int cx, int cy, double radius, Pixel p, double factor) {
		// TODO Auto-generated method stub
		return this.paintPicture(this.paintPictureArray(width, height, upper_left, upper_right, lower_left, lower_right)).paint(cx, cy, radius, p,factor);
	}

}
