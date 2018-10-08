package a3;

public class ImmutablePixelArrayPicture implements Picture{
	final private int width;
	final private int height;  
	final private Pixel[][] picture; 
	 
	// constructor 1
		// Creates new object using values provided by pixel_array, matching in size. 
		public ImmutablePixelArrayPicture(Pixel[][] pixel_array) {
			
			if(pixel_array == null || pixel_array.length == 0 || pixel_array[0].length == 0) {
				throw new IllegalArgumentException("invalid input");
			}
			else {
				for(int i = 0; i<pixel_array.length;i++) {
					if(pixel_array[i] == null || pixel_array[i].length != pixel_array[0].length ) {
						throw new IllegalArgumentException("invalid input");
					}
					else {
						for(int j = 0; j < pixel_array[0].length; j++) {
							if(pixel_array[i][j] == null) {
								throw new IllegalArgumentException("invalid input");
							}
						}
					}
				}
			}
			 
				
				this.width = pixel_array.length;
				this.height = pixel_array[0].length;
				
				Pixel[][] picture = new Pixel[width][height];
				this.picture = pixel_array.clone();
			
		}
		
		// constructor 2
		// Creates new object by providing geometry of picture and an initial value for all pixels.
		public ImmutablePixelArrayPicture(int width, int height, Pixel initial_value) {
			if(width <= 0 || height <= 0 || initial_value == null) {
				throw new IllegalArgumentException("invalid input");
			}else {
				this.width = width;
				this.height = height;
				picture = new Pixel[width][height];
		        for (int j = 0; j < width; j++) {
		            for (int i = 0; i < height; i++) {
		                picture[j][i] = initial_value; 
		            }
		        }
			}
		}
		
		// constructor 3
		// Creates new object by providing geometry of picture. 
		// Initial value of all pixels should be medium gray (i.e., a grayscale pixel with intensity 0.5)
		public ImmutablePixelArrayPicture(int width, int height) {
			if(width <= 0 || height <= 0) {
				throw new IllegalArgumentException("invalid input");
			}else {
				this.width = width;
				this.height = height;
				picture = new Pixel[width][height];
		        for (int j = 0; j < width; j++) {
		            for (int i = 0; i < height; i++) {
		                picture[j][i] = new GrayPixel(0.5);
		            }
		        }
			}
		}
		
		public Picture paintPicture() {
			// create a new Picture object
			MutablePixelArrayPicture NewPicture = new MutablePixelArrayPicture(picture);		
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
			if(x<0 || y<0 || x>= width || y>=height) {
				throw new IllegalArgumentException("invalid input");
			}else {
				return this.picture[x][y];
			}
		}

	@Override
	public Picture paint(int x, int y, Pixel p) {
		// TODO Auto-generated method stub
		Picture NewPicture = paintPicture();
		NewPicture.paint(x, y, p);
		return NewPicture;
	}

	@Override
	public Picture paint(int x, int y, Pixel p, double factor) {
		// TODO Auto-generated method stub
		Picture NewPicture = paintPicture();
		NewPicture.paint(x, y, p,factor);
		return NewPicture;
	}

	@Override
	public Picture paint(int ax, int ay, int bx, int by, Pixel p) {
		// TODO Auto-generated method stub
		Picture NewPicture = paintPicture();
		NewPicture.paint(ax, ay, bx, by, p);
		return NewPicture;
	}

	@Override
	public Picture paint(int ax, int ay, int bx, int by, Pixel p, double factor) {
		// TODO Auto-generated method stub
		Picture NewPicture = paintPicture();
		NewPicture.paint(ax, ay, bx, by, p,factor);
		return NewPicture;
	}

	@Override
	public Picture paint(int cx, int cy, double radius, Pixel p) {
		// TODO Auto-generated method stub
		Picture NewPicture = paintPicture();
		NewPicture.paint(cx, cy, radius, p);
		return NewPicture;
	}

	@Override
	public Picture paint(int cx, int cy, double radius, Pixel p, double factor) {
		// TODO Auto-generated method stub
		Picture NewPicture = paintPicture();
		NewPicture.paint(cx, cy, radius, p,factor);
		return NewPicture;
	}

}
