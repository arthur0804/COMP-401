package a3;

public class GammaCorrect implements PixelTransformation {
	private double gamma;
	    
	public GammaCorrect (double gamma) {
		this.gamma = gamma;
	}
	
	@Override
	public Pixel transform(Pixel p) {
		// TODO Auto-generated method stub
		double red = p.getRed();
		double blue = p.getBlue();
		double green = p.getGreen();
		
		red = Math.pow(red, (1.0/gamma));
		blue = Math.pow(blue, (1.0/gamma));
		green = Math.pow(green, (1.0/gamma));
		
		ColorPixel NewPixel = new ColorPixel(red, green, blue);
		
		return NewPixel;
	}

}
