package a5;

//class of pixel array picture, parent class of mutable/immutable pixel array pictures
public abstract class PixelArrayPicture extends PictureImpl implements Picture{
	protected Pixel[][] pixelarray;
	
	// constructor
	public PixelArrayPicture(Pixel[][] parray, String caption) {
		super(caption);
		// call the copyArray method to store the value
		this.pixelarray = copyArray(parray);
	}

	@Override
	public int getWidth() {
		// get width of the picture
		return pixelarray.length;
	}

	@Override
	public int getHeight() {
		// get height of the picture
		return pixelarray[0].length;
	}

	@Override
	public Pixel getPixel(int x, int y) {
		// check parameters: x and y should not be out of the boundaries
		if ((x < 0) || (x >= getWidth()) || (y < 0) || (y >= getHeight())) {
			throw new IllegalArgumentException("X or Y is out of range");
		}
		return pixelarray[x][y];
	}
	
	// copy the input pixel array
	private static Pixel[][] copyArray(Pixel[][] pixel_array) {
		/*
		 * check input parameters
		 * array should not be null
		 * pixel in array should not be null
		 * should have the same dimensions
		 */
		
		if (pixel_array == null) {
			throw new IllegalArgumentException("pixel_array is null");
		}
		int width = pixel_array.length;
		if (width == 0) {
			throw new IllegalArgumentException("pixel_array has illegal geometry");
		}
		for (int x = 0; x < width; x++) {
			if (pixel_array[x] == null) {
				throw new IllegalArgumentException("pixel_array includes null columns");
			}
		}
		int height = pixel_array[0].length;
		if (height == 0) {
			throw new IllegalArgumentException("pixel_array has illegal geometry");
		}
		for (int x = 0; x < width; x++) {
			if (pixel_array[x].length != height) {
				throw new IllegalArgumentException("Columns in picture are not all the same height.");
			}
		}
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if (pixel_array[x][y] == null) {
					throw new IllegalArgumentException("pixel_array includes null pixels");
				}
			}
		}
		Pixel[][] copy = new Pixel[width][height];
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				copy[x][y] = pixel_array[x][y];
			}
		}
		return copy;
	}
}
