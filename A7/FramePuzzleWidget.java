package a7;

import java.awt.*;
import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
 
public class FramePuzzleWidget extends JPanel implements KeyListener, MouseListener {
	private PictureView picture_view;
	private Picture WholePicture, BlankSection;
	private Picture[][] picture_array;
	private int SectionWidth, SectionHeight;
	private int blankX, blankY;
	
	public FramePuzzleWidget(Picture p) {
		setLayout(new BorderLayout());
	
		// create a picture array to put 25 sections
		picture_array = new Picture[5][5];
		
		// divided by 5
		SectionWidth = p.getWidth()/5;
		SectionHeight = p.getHeight()/5;
		
		// initial position of the blank section, i.e. picture[4][4]
		blankX = 4;
		blankY = 4;
		
		// construct 5x5 sections
		for(int i=0;i<5;i++){
			for(int j=0;j<5;j++){
				Picture section = p.extract(i*SectionWidth, j*SectionHeight, SectionWidth, SectionHeight);
				picture_array[i][j] = section;
			}
		}
		
		//create an empty section with white pixels
		Pixel[][] parray = new Pixel[SectionWidth][SectionHeight];
		for(int i =0;i<SectionWidth;i++){
			for(int j=0;j<SectionHeight;j++){
				parray[i][j] = new ColorPixel(1.0,1.0,1.0); 
			}
		}
		BlankSection = new MutablePixelArrayPicture(parray, "blank section");		
		
		// right corner is the blank picture
		picture_array[4][4] = BlankSection; 
		
		// combine 25 sections into a whole picture
		WholePicture = setPicture(picture_array);
		
		// put into picture view
		picture_view = new PictureView(WholePicture.createObservable());
		
		picture_view.addMouseListener(this);
		picture_view.addKeyListener(this);
		picture_view.setFocusable(true);
		add(picture_view);
		
	}
		
	// helper function: combine 25 sections
	private static Picture setPicture(Picture[][] picture_array) {
		// width and height of a section
		int width = picture_array[0][0].getWidth();
		int height = picture_array[0][0].getHeight();
		
		// the total picture should multiply 5
		Pixel [][] parray = new Pixel[width*5][height*5];
		
		// put pixels in 
		for(int x=0; x < width*5; x++) {
			for(int y=0; y < height*5; y++) {
				Pixel pixel = picture_array[x/width][y/height].getPixel(x%width, y%height);
				parray[x][y] = pixel;
			}
		}
		
		// put into the whole picture of 5x5 sections 
		Picture WholePicture = new MutablePixelArrayPicture(parray, "Puzzle 5x5 Picture");	
		return WholePicture;
	}
	
	// helper function: change the position of two sections
	private void changePosition(int x0, int y0, int x1, int y1) {
		Picture temp = BlankSection;
		
		// (x0,y0) is the position of blank section
		// (x1,y1) is the position of clicked section
		picture_array[x0][y0] = picture_array[x1][y1]; 
		picture_array[x1][y1] = temp;
		
		// same as above: combine into a whole picture and add to the picture view
		WholePicture = setPicture(picture_array);
		picture_view.setPicture(WholePicture.createObservable());
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// get where is the click
		int clickedX = e.getX() / SectionWidth;
		int clickedY = e.getY() / SectionHeight;
		
		/*
		 * the variable interval shows how many times the blank section
		 * needs to move left/right/up/down
		 * i.e. if the user clicks the adjacent section, the interval will be 1
		 * if the user clickes sections far away, the interval will be 2,3,4
		 */
		if (clickedY == blankY) {
			// in a column
			if (clickedX < blankX) {
				// blank section moves left
				int interval = blankX - clickedX;
				for (int i = 0; i < interval; i++) {
					changePosition(blankX, blankY, blankX-1, blankY);
					blankX--;
				}
			}else{
				// blank section moves right
				int interval = clickedX - blankX;
				for (int i = 0; i < interval; i++) {
					changePosition(blankX, blankY, blankX+1, blankY);
					blankX++;
				}
			}
		}
		if (clickedX == blankX){
			// in a row
			if(clickedY < blankY) {
				// blank section moves up
				int interval = blankY - clickedY;
				for (int i = 0; i < interval; i++) {
					changePosition(blankX, blankY, blankX, blankY-1);
					blankY--;
				}
			}else {
				// blank section moves down
				int interval = clickedY - blankY;
				for (int i = 0; i < interval; i++) {
					changePosition(blankX, blankY, blankX, blankY+1);
					blankY++;
				}
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()){
			case KeyEvent.VK_LEFT:
				if(blankX > 0) {
					changePosition(blankX, blankY, blankX-1, blankY);
					blankX--;
				}
				break;
			case KeyEvent.VK_RIGHT:
				if(blankX < 4) {
					changePosition(blankX, blankY, blankX+1, blankY);
					blankX++;
				}
				break;			
			case KeyEvent.VK_UP:
				if(blankY > 0) {
					changePosition(blankX, blankY, blankX, blankY-1);
					blankY--;
				}
				break;				
			case KeyEvent.VK_DOWN:
				if(blankY < 4) {
					changePosition(blankX, blankY, blankX, blankY+1);
					blankY++;
				}
				break;
		}		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
