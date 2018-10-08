package a3;

public class Threshold implements PixelTransformation {
	private double threshold;
	
	public Threshold (double threshold) {
		this.threshold = threshold;
	}  
	
	@Override
	public Pixel transform(Pixel p) {
		// TODO Auto-generated method stub
		if(p.getIntensity() > threshold) {
			return Pixel.WHITE;
		}else {
			return Pixel.BLACK;
		}
	} 

}
