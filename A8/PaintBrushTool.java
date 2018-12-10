package a8;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class PaintBrushTool implements Tool, ChangeListener, ActionListener {

	private PaintBrushToolUI ui;
	private ImageEditorController controller;
	private int brush_size;
	private Pixel brush_color;
	private int opacity_value;
	private String shape;
	
	public PaintBrushTool(ImageEditorController controller) {
		this.controller = controller;
		ui = new PaintBrushToolUI(this);
		setBrushColor(new ColorPixel(0.5, 0.5, 0.5));
		setBrushSize(5);
		setOpacityValue(0);
		shape = "rectangle";
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		brush_color = ui.GetColor();
		ui.setColor(brush_color);
		brush_size = ui.getBrushSize();
		opacity_value = ui.getOpacityValue();
	}

	public void setBrushColor(Pixel p) {
		brush_color = p;
		ui.setColor(p);
	}

	public void setBrushSize(int size) {
		brush_size = size;
		ui.setBrushSize(size);
	}

	private void setOpacityValue(int i) {
		opacity_value = i;
		ui.setOpacityValue(i);		
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mousePressed(MouseEvent e) {
		controller.getModel().createCopyOfCurrent();
		if(shape.equals("rectangle")) {
			controller.getModel().paintAtRectangle(e.getX(), e.getY(), brush_color, brush_size, opacity_value);
		}else {
			controller.getModel().paintAtCircle(e.getX(), e.getY(), brush_color, brush_size, opacity_value);
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
			controller.getModel().paintAtRectangle(e.getX(), e.getY(), brush_color, brush_size, opacity_value);
		}else {
			controller.getModel().paintAtCircle(e.getX(), e.getY(), brush_color, brush_size, opacity_value);
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getName() {
		return "Paint Brush";
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
