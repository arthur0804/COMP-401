package a3;


public class MutablePixelArrayPicture implements Picture {

	private int width;
	private int height;
	private Pixel[][] picture; 

	// constructor 1
	// Creates new object using values provided by pixel_array, matching in size. 
	public MutablePixelArrayPicture(Pixel[][] pixel_array) {
		if(pixel_array == null || pixel_array.length == 0 || pixel_array[0].length == 0) {
			throw new IllegalArgumentException("invalid input");
		}else {
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
			this.picture = new Pixel[this.width][this.height];
			for(int i=0;i<pixel_array.length;i++) {
			    for(int j=0;j<pixel_array[0].length;j++) {
				picture[i][j]=pixel_array[i][j];
			    }
			}
	}
	
	// constructor 2
	// Creates new object by providing geometry of picture and an initial value for all pixels.
	public MutablePixelArrayPicture(int width, int height, Pixel initial_value) {
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
	public MutablePixelArrayPicture(int width, int height) {
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
		if(x<0 || y<0 || p == null|| x>= width || y>=height) {
			throw new IllegalArgumentException("invalid input");
		}else {
			this.picture[x][y] = p;
		}
		return this;
	}

	@Override
	public Picture paint(int x, int y, Pixel p, double factor) {
		// TODO Auto-generated method stub
		if(x < 0 || y < 0 || factor < 0 || factor >1 || x>= width || y>=height || p == null) {
			throw new IllegalArgumentException("invalid input");
		}else {
			this.picture[x][y].blend(p, factor);
		}
		return this;
	}

	public Picture paint(int ax, int ay, int bx, int by, Pixel p) {
		// TODO Auto-generated method stub	
		// should not be smaller than 0

		// should not be larger than width and height
		if(ax > this.width || bx > this.width || ay > this.height || by > this.height || p == null) {
			throw new IllegalArgumentException("invalid input");
		}
		
		if (ax<0) {
		    ax=0;
		}
		if (ay<0) {
		    ay=0;
		}
		if (bx<0) {
		    bx=0;
		}
		if (by<0) {
		    by=0;
		}
		 
		
		if(ax>=0 && bx>=0 && ay>=0 && by>=0) {
		if((ax < bx) && (ay < by)) {
			// (ax,ay) is the upper left and (bx,by) is the lower right
			for(int x=ax; x<=bx; x++) {
				for(int y=ay; y<=by;y++) {
					this.picture[x][y] = p;
				}
			}
		}else if ((bx < ax) && (by < ay)) {
			// (bx,by) is the upper left and (ax,ay) is the lower right
			for(int x=bx; x<=ax; x++) {
				for(int y=by; y<=ay;y++) {
					this.picture[x][y] = p;
				}
			}
		}else if ((ax < bx) && (ay > by)){
			// (ax,ay) is the lower left and (bx,by) is the upper right
			for(int x=ax; x<=bx; x++) {
				for(int y=by; y<=ay;y++) {
					this.picture[x][y] = p;
				}
			}
		}else if ((bx < ax) && (by > ay)) {
			// (bx,by) is the lower left and (ax,ay) is the upper right
			for(int x=bx; x<=ax; x++) {
				for(int y=ay; y<=by;y++) {
					this.picture[x][y] = p;
				}
			}
		}else if (by == ay) {
			// same row
			if (bx > ax) {
			// (bx,by) is on the right
				for(int x = ax; x <= bx; x++) {
					this.picture[x][ay] = p;
				}
			}else {
			// (ax,ay) is on the right
				for(int x = bx; x <= ax; x++) {
					this.picture[x][ay] = p;
				}
			}
		}else if (bx == ax) {
			// same column
			if (by > ay) {
			// (bx,by) is on the bottom
				for(int y = ay; y <= by; y++) {
					this.picture[ax][y] = p;
				}
			}else {
			// (ax,ay) is on the bottom
				for(int y = by; y <= ay; y++) {
					this.picture[ax][y] = p;
				}
			}
		}else if ((ax == bx) && (ay == by)) {
			// just one pixel
			this.picture[ax][ay] = p;
		}else {
			throw new IllegalArgumentException("in valid input");
		}
		}
		return this;
	}

	@Override
	public Picture paint(int ax, int ay, int bx, int by, Pixel p, double factor) {
		// TODO Auto-generated method stub
		
		// should not be larger than width and height
				if(ax > this.width || bx > this.width || ay > this.height || by > this.height || p == null) {
					throw new IllegalArgumentException("invalid input");
				}
				
				if (ax<0) {
				    ax=0;
				}
				if (ay<0) {
				    ay=0;
				}
				if (bx<0) {
				    bx=0;
				}
				if (by<0) {
				    by=0;
				}
		
		if(ax>=0 && bx>=0 && ay>=0 && by>=0) {
		if((ax < bx) && (ay < by)) {
			// (ax,ay) is the upper left and (bx,by) is the lower right
			for(int x=ax; x<=bx; x++) {
				for(int y=ay; y<=by;y++) {
					this.picture[x][y] = this.picture[x][y].blend(p, factor);;
				}
			}
		}else if ((bx < ax) && (by < ay)) {
			// (bx,by) is the upper left and (ax,ay) is the lower right
			for(int x=bx; x<=ax; x++) {
				for(int y=by; y<=ay;y++) {
					this.picture[x][y] = this.picture[x][y].blend(p, factor);;
				}
			}
		}else if ((ax < bx) && (ay > by)){
			// (ax,ay) is the lower left and (bx,by) is the upper right
			for(int x=ax; x<=bx; x++) {
				for(int y=by; y<=ay;y++) {
					this.picture[x][y] = this.picture[x][y].blend(p, factor);;
				}
			}
		}else if ((bx < ax) && (by > ay)) {
			// (bx,by) is the lower left and (ax,ay) is the upper right
			for(int x=bx; x<=ax; x++) {
				for(int y=ay; y<=by;y++) {
					this.picture[x][y] = this.picture[x][y].blend(p, factor);;
				}
			}
		}else if (by == ay) {
			// same row
			if (bx > ax) {
			// (bx,by) is on the right
				for(int x = ax; x <= bx; x++) {
					this.picture[x][ay] = this.picture[x][ay].blend(p, factor);
				}
			}else {
			// (ax,ay) is on the right
				for(int x = bx; x <= ax; x++) {
					this.picture[x][ay] = this.picture[x][ay].blend(p, factor);
				}
			} 
		}else if (bx == ax) {
			// same column
			if (by > ay) {
			// (bx,by) is on the bottom
				for(int y = ay; y <= by; y++) {
					this.picture[ax][y] = this.picture[ax][y].blend(p, factor);
				}
			}else {
			// (ax,ay) is on the bottom
				for(int y = by; y <= ay; y++) {
					this.picture[ax][y] = this.picture[ax][y].blend(p, factor);
				}
			}
		}else if ((ax == bx) && (ay == by)) {
			// just one pixel
			this.picture[ax][ay] = this.picture[ax][ay].blend(p, factor);
		}else {
			throw new IllegalArgumentException("in valid input");
		}
		}
		return this;
	}

	@Override
	public Picture paint(int cx, int cy, double radius, Pixel p) {
		// TODO Auto-generated method stub
		if(p == null || radius <= 0.0) {
			throw new IllegalArgumentException("invalid input");
		}
		
		for(int x=0; x<width; x++) {
			for(int y=0; y<height; y++) {
				double distance = Math.sqrt((x-cx)*(x-cx)+(y-cy)*(y-cy));
				if(distance <= radius) {
					this.picture[x][y] = p;
				}
			}
		}
		return this;
	}

	@Override
	public Picture paint(int cx, int cy, double radius, Pixel p, double factor) {
		// TODO Auto-generated method stub
		if(p == null || radius <= 0.0) {
			throw new IllegalArgumentException("invalid input");
		}
		
		for(int x=0; x<width; x++) {
			for(int y=0; y<height; y++) {
				double distance = Math.sqrt((x-cx)*(x-cx)+(y-cy)*(y-cy));
				if(distance <= radius) {
					this.picture[x][y] = this.picture[x][y].blend(p, factor);
				}
			}
		}
		return this;
	}
}
