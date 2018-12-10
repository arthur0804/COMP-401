package a7;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
 
public class ImageAdjusterWidget extends JPanel implements ChangeListener {
	private Picture picture;
	private PictureView picture_view;

	private JSlider blur_slider;
	private JSlider saturation_slider;
	private JSlider brightness_slider;
	
	public ImageAdjusterWidget(Picture p) {
		setLayout(new BorderLayout());
		
		// get a copy of the picture
		this.picture = copy(p);
		
		// add to picture_view
		this.picture_view = new PictureView(p.createObservable());
	    add(picture_view, BorderLayout.CENTER);
	            
        // blur
        blur_slider = new JSlider(0, 5, 0);
        blur_slider.setPaintTicks(true);
        blur_slider.setSnapToTicks(true);
        blur_slider.setPaintLabels(true);
        blur_slider.setMajorTickSpacing(1);

        // saturation
        saturation_slider = new JSlider(-100, 100, 0);
        saturation_slider.setPaintTicks(true);
        saturation_slider.setPaintLabels(true);
        saturation_slider.setMajorTickSpacing(25);

        // bright
        brightness_slider = new JSlider(-100, 100, 0);
        brightness_slider.setPaintTicks(true);
        brightness_slider.setPaintLabels(true);
        brightness_slider.setMajorTickSpacing(25);

        // add listener
        blur_slider.addChangeListener(this);
        saturation_slider.addChangeListener(this);
        brightness_slider.addChangeListener(this);
        
        // combine slider with label
        JPanel panel = new JPanel(new GridLayout(3, 2));
        panel.setPreferredSize(new Dimension(200, 300));       
        panel.add(new JLabel("Blur"));
        panel.add(blur_slider);
        panel.add(new JLabel("Saturation"));
        panel.add(saturation_slider);
        panel.add(new JLabel("Brightness"));
        panel.add(brightness_slider);
        
        add(panel, BorderLayout.SOUTH);     
	}
	
	// helper function: get a copy of the picture
	private static Picture copy(Picture p) {
		Pixel[][] parray = new Pixel[p.getWidth()][p.getHeight()];
		for (int x=0; x<p.getWidth(); x++) {
			for (int y=0; y<p.getHeight(); y++) {
				parray[x][y] = p.getPixel(x, y);
			}
		} 
		Picture copy = new MutablePixelArrayPicture(parray, p.getCaption());
		return copy;
	}
	
	@Override
	public void stateChanged(ChangeEvent e) {
		 if (! blur_slider.getValueIsAdjusting() &&
		     ! saturation_slider.getValueIsAdjusting() &&
		     ! brightness_slider.getValueIsAdjusting()) {
			 
			 // call three adjustments consecutively	 
			 Picture temp = blurPicture(this.picture, blur_slider.getValue());
			 temp = brightenPicture(temp, brightness_slider.getValue());
			 temp = saturatePicture(temp, saturation_slider.getValue());

			 // change the picture
			 picture_view.setPicture(temp.createObservable());		
		  }	
	}
	
	/*
	 * adjuster 1: brighten
	 */
	private Picture brightenPicture(Picture p, int value) {
		// get a copy of the this.picture (initial state)
		Picture brightenedPicture = copy(this.picture);
		
		// make some changes
		for (int x = 0; x < brightenedPicture.getWidth(); x++) {
			for (int y = 0; y < brightenedPicture.getHeight(); y++) {
				if (value > 0) {
					Pixel pixel = p.getPixel(x, y).lighten(value / 100.0);
					brightenedPicture.paint(x, y, pixel, 1.0D);
				}else if(value < 0){
					Pixel pixel = p.getPixel(x, y).darken(Math.abs(value / 100.0)); // change to positive
					brightenedPicture.paint(x, y, pixel, 1.0D);
				}else {
					brightenedPicture.paint(x, y, p.getPixel(x, y), 1.0D);
				}
			}
		}	
		return brightenedPicture;
	}

	/*
	 * adjuster 2: saturate
	 */
	private Picture saturatePicture(Picture p, int value) {
		// get a copy of the this.picture (initial state)
		Picture saturatedPicture = copy(this.picture);
		
		// make some changes
		for (int i = 0; i < saturatedPicture.getWidth(); i++) {
			for (int j = 0; j < saturatedPicture.getHeight(); j++) {
				saturatedPicture.paint(i, j, saturatePixel(p.getPixel(i, j), value), 1.0D);
			}
		}	
		return saturatedPicture;
	}

	private Pixel saturatePixel(Pixel pixel, int value) {
		// saturate a pixel by creating a new pixel with RGB values
		double red = computeSaturation(pixel, pixel.getRed(), value);
		double green = computeSaturation(pixel, pixel.getGreen(), value);
		double blue = computeSaturation(pixel, pixel.getBlue(), value);

		return new ColorPixel(red, green, blue);
	}
	

	private double computeSaturation(Pixel pixel, double color, int value) {
		// compute the RGB values for the saturated pixel
		double new_color;
		if(value <= 0) {
			new_color = color * (1.0 + (value / 100.0) ) - (pixel.getIntensity() * value / 100.0);
		}else {
			// get the largest component
			double lc = Math.max(pixel.getRed(), pixel.getGreen());
			lc = Math.max(lc, pixel.getBlue());
			
			// check whether is close to zero
			if(lc < 0.01) {
				new_color = 0.0;
			}else {
				new_color = color * ((lc + ((1.0 - lc) * (value / 100.0))) / lc);
			}
		}
		return new_color;
	}

	/*
	 * adjuster 3: blur
	 */
	private Picture blurPicture(Picture p, int value) {
		// get a copy of the this.picture (initial state)
		Picture blurredPicture = copy(this.picture);
		
		// make some changes
		for (int i = 0; i < blurredPicture.getWidth(); i++) {
			for (int j = 0; j < blurredPicture.getHeight(); j++) {
				int left, right, top, bottom, size;
				// check for boundaries
				left = i - value >= 0 ? i - value : 0;
				right = i + value <= blurredPicture.getWidth() - 1 ? i + value
						: blurredPicture.getWidth() - 1;
				top = j - value >= 0 ? j - value : 0;
				bottom = j + value <= blurredPicture.getHeight() - 1 ? j + value
						: blurredPicture.getHeight() - 1;
				// get how many pixels
				size = (right - left + 1) * (bottom - top + 1);
				
				double red = 0, green = 0, blue = 0;
				// get all the adjacent pixels' RGB
				for (int x = left; x <= right; x++) {
					for (int y = top; y <= bottom; y++) {
						red += p.getPixel(x, y).getRed();
						green += p.getPixel(x, y).getGreen();
						blue += p.getPixel(x, y).getBlue();
					}
				}
				Pixel blurredPixel = new ColorPixel(red / size, green / size, blue / size);
				blurredPicture.paint(i, j, blurredPixel, 1.0D);
			}
		}
		return blurredPicture;
	}
}
