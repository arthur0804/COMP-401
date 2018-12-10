package a8;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ImageEditorModel {

	private Picture original;
	// store the changes of history pictures
	private List<ObservablePicture> history =  new ArrayList<>();
	// index of current picture
	private int current_pic;
	
	public ImageEditorModel(Picture f) {
		original = f;
		history.add(original.copy(true).createObservable());
		current_pic = 0;
	}

	public ObservablePicture getCurrent() {
		return history.get(current_pic);
	}

	public Pixel getPixel(int x, int y) {
		return getCurrent().getPixel(x, y);
	}

	public void blurAtRectangle(int x, int y, int blur_size) {
		getCurrent().suspendObservable();
		// blur in rectangular area
		for (int xposition = x-blur_size+1; xposition <= x+blur_size-1; xposition++) {
			for (int yposition = y-blur_size+1; yposition <= y+blur_size-1; yposition++) {
				try {
					int left, right, top, bottom, count;
					// get boundaries
					left = xposition - blur_size >=0 ? xposition - blur_size : 0;
					right = xposition + blur_size <= getCurrent().getWidth() - 1 ? 
							xposition + blur_size : getCurrent().getWidth() - 1;
					top = yposition - blur_size >=0 ? yposition-blur_size : 0;
					bottom = yposition + blur_size <= getCurrent().getHeight() - 1?
							yposition + blur_size : getCurrent().getHeight() - 1;
					count = (right - left + 1)* (bottom - top + 1);
					
					double red = 0, green = 0, blue = 0;
					// get all the adjacent pixels' RGB
					for (int i = left; i <= right; i++) {
						for (int j = top; j <= bottom; j++) {
							red += getCurrent().getPixel(i,j).getRed();
							green += getCurrent().getPixel(i,j).getGreen();
							blue += getCurrent().getPixel(i,j).getBlue();
						}
					}
					Pixel blurredPixel = new ColorPixel(red / count, green / count, blue / count);
					// paint 
					getCurrent().paint(xposition, yposition, blurredPixel);		
				}catch (Exception e) {
					// do nothing if out of boundary
				}		
			}
		}
		// release
		getCurrent().resumeObservable();
	}
	
	public void blurAtCircle(int x, int y, int blur_size) {
		getCurrent().suspendObservable();
		// blur in rectangular area
		for (int xposition = x-blur_size+1; xposition <= x+blur_size-1; xposition++) {
			for (int yposition = y-blur_size+1; yposition <= y+blur_size-1; yposition++) {
				try {
					if(Math.sqrt((xposition - x)*(xposition - x) + (yposition -y)*(yposition -y)) <= blur_size) {
						int left, right, top, bottom, count;
						// get boundaries
						left = xposition - blur_size >=0 ? xposition - blur_size : 0;
						right = xposition + blur_size <= getCurrent().getWidth() - 1 ? 
								xposition + blur_size : getCurrent().getWidth() - 1;
						top = yposition - blur_size >=0 ? yposition-blur_size : 0;
						bottom = yposition + blur_size <= getCurrent().getHeight() - 1?
								yposition + blur_size : getCurrent().getHeight() - 1;
						count = (right - left + 1)* (bottom - top + 1);
								
						double red = 0, green = 0, blue = 0;
						// get all the adjacent pixels' RGB
						for (int i = left; i <= right; i++) {
							for (int j = top; j <= bottom; j++) {
								red += getCurrent().getPixel(i,j).getRed();
								green += getCurrent().getPixel(i,j).getGreen();
								blue += getCurrent().getPixel(i,j).getBlue();
							}
						}
						Pixel blurredPixel = new ColorPixel(red / count, green / count, blue / count);
						// paint 
						getCurrent().paint(xposition, yposition, blurredPixel);		
					}		
				}catch (Exception e) {
					// do nothing if out of boundary
				}		
			}
		}
		
		// release
		getCurrent().resumeObservable();
	}
	
	public void paintAtRectangle(int x, int y, Pixel brushColor, int brush_size, int opacity_value) {
		getCurrent().suspendObservable();
		// paint in rectangular area
		for (int xposition = x-brush_size+1; xposition <= x+brush_size-1; xposition++) {
			for (int yposition = y-brush_size+1; yposition <= y+brush_size-1; yposition++) {
				try {
					// blend the brush color with the pixel in picture
					double factor = opacity_value / 100.0;
					Pixel original_pixel = getCurrent().getPixel(xposition, yposition);
					Pixel blended_pixel	= brushColor.blend(original_pixel, factor);			
					// paint 
					getCurrent().paint(xposition, yposition, blended_pixel);		
				}catch (Exception e) {
					// do nothing if out of boundary
				}		
			}
		}
		// release
		getCurrent().resumeObservable();
	}
		
	public void paintAtCircle(int x, int y, Pixel brushColor, int brush_size, int opacity_value) {
		getCurrent().suspendObservable();
		//paint in circle area
		for (int xposition = x-brush_size+1; xposition <= x+brush_size-1; xposition++) {
			for (int yposition = y-brush_size+1; yposition <= y+brush_size-1; yposition++) {
				try {
					// blend the brush color with the pixel in picture
					if(Math.sqrt((xposition - x)*(xposition - x) + (yposition -y)*(yposition -y)) <= brush_size){
						double factor = opacity_value / 100.0;
						Pixel original_pixel = getCurrent().getPixel(xposition, yposition);
						Pixel blended_pixel	= brushColor.blend(original_pixel, factor);			
						// paint 
						getCurrent().paint(xposition, yposition, blended_pixel);	
					}
				}catch (Exception e) {
					// do nothing if out of boundary
				}		
			}
		}
		// release
		getCurrent().resumeObservable();
	}
	
	public void setPicture(String url) throws IOException{
		// read picture from url
		history.add(A8Helper.readFromURL(url).createObservable());
		current_pic++;
	}
	
	// create a copy of current picture and add into the history
	public void createCopyOfCurrent() {
		while (history.size() > current_pic+1) {
			// remove pictures in history which are after the current picture
			history.remove(history.size()-1);
		}
		history.add(copy(getCurrent()).createObservable());
		current_pic++;
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
	
	public void undo() {
		if (current_pic == 0) {
			System.out.println("No earlier version.");
		} else {
			current_pic--;
		}
	}

	public void redo() {
		if (current_pic == history.size()-1) {
			System.out.println("No newer version");
		} else {
			current_pic++;
		}
	}
	
	
}
