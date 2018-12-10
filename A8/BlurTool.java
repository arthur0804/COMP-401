package a8;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class BlurTool implements Tool, ChangeListener, ActionListener {

	private BlurToolUI ui;
	private ImageEditorController controller;
	private int blur_size;
	private String shape;
	
	public BlurTool (ImageEditorController controller) {
		this.controller = controller;
		ui = new BlurToolUI(this);
		setBlurSize(1);
		shape = "rectangle";
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		blur_size = ui.getBlurSize();
	}

	public void setBlurSize(int size) {
		blur_size = size;
		ui.setBlurSize(size);
	}


	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mousePressed(MouseEvent e) {
		controller.getModel().createCopyOfCurrent();
		if(shape.equals("rectangle")) {
			controller.getModel().blurAtRectangle(e.getX(), e.getY(), blur_size);
		}else {
			controller.getModel().blurAtCircle(e.getX(), e.getY(), blur_size);
		}
		
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
	public void mouseDragged(MouseEvent e) {
		if(shape.equals("rectangle")) {
			controller.getModel().blurAtRectangle(e.getX(), e.getY(), blur_size);
		}else {
			controller.getModel().blurAtCircle(e.getX(), e.getY(), blur_size);
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getName() {
		return "Blur";
	}

	@Override
	public JPanel getUI() {
		return ui;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("rectangle")) {
			shape = "rectangle";
		}else {
			shape = "circle";
		}
	}
}
