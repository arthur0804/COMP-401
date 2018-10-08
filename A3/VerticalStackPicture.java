package a3;
 
public class VerticalStackPicture implements Picture {
	private int width;
	private int height; 
	private Pixel[][] picture; 
	 
	public VerticalStackPicture(Picture top, Picture bottom) {
		if(top == null || bottom == null) {
			throw new IllegalArgumentException("in valid input");
		}else if(top.getWidth() != bottom.getWidth()) {
			throw new IllegalArgumentException("in valid input");
		}else {
			this.width = top.getWidth();
			this.height = top.getHeight() + bottom.getHeight();
			picture = new Pixel[width][height];
			
			// copy top picture
			for (int x = 0; x < top.getWidth(); x++) {
	            for (int y = 0; y < top.getHeight(); y++) {
	                picture[x][y] = top.getPixel(x, y);
	            }
	        }
		
			// copy bottom picture
			for (int x = 0; x < bottom.getWidth(); x++) {
	            for (int y = 0; y < bottom.getHeight(); y++) {
	                picture[x][top.getHeight()+y] = bottom.getPixel(x, y);
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
		if(x<0 || y<0 || x >= width || y >= height) {
			throw new IllegalArgumentException("in valid input");
		}else {
			return this.picture[x][y];
		}
	}

	@Override
	public Picture paint(int x, int y, Pixel p) {
		// TODO Auto-generated method stub
		if(x<0 || y<0 || p == null || x >= width || y >= height) {
			throw new IllegalArgumentException("in valid input");
		}else {
			this.picture[x][y] = p;
		}
		return this;
	}

	@Override
	public Picture paint(int x, int y, Pixel p, double factor) {
		// TODO Auto-generated method stub
		if(x < 0 || y < 0 || factor < 0 || factor >1 || x >= width || y >= height || p == null) {
			throw new IllegalArgumentException("in valid input");
		}else {
			this.picture[x][y] = this.picture[x][y].blend(p, factor);
		}
		return this;
	}

	@Override
	public Picture paint(int ax, int ay, int bx, int by, Pixel p) {
		// TODO Auto-generated method stub	
		// should not be smaller than 0
		if(ax < 0 || ay <0 || bx <0 || by < 0 || p == null) {
			throw new IllegalArgumentException("in valid input");
		}
		
		// should not be larger than width and height
		if(ax > this.width || bx > this.width || ay > this.height || by > this.height ) {
			throw new IllegalArgumentException("in valid input");
		}
		
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
		return this;
	}

	@Override
	public Picture paint(int ax, int ay, int bx, int by, Pixel p, double factor) {
		// TODO Auto-generated method stub
		// should not be smaller than 0
		if(ax < 0 || ay <0 || bx <0 || by < 0 || p == null) {
			throw new IllegalArgumentException("in valid input");
		}
		
		// should not be larger than width and height
		if(ax > this.width || bx > this.width || ay > this.height || by > this.height ) {
			throw new IllegalArgumentException("in valid input");
		}
		
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
		return this;
	}

	@Override
	public Picture paint(int cx, int cy, double radius, Pixel p) {
		// TODO Auto-generated method stub
		if(p == null || radius <= 0.0) {
			throw new IllegalArgumentException("in valid input");
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
			throw new IllegalArgumentException("in valid input");
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
