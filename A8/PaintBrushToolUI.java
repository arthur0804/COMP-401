package a8;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;

public class PaintBrushToolUI extends JPanel {

	private JSlider red_slider;
	private JSlider green_slider;
	private JSlider blue_slider;
	private JSlider size_slider;
	private JSlider opacity_slider;
	private PictureView color_preview;
	private JRadioButton rectangle;
	private JRadioButton circle;
	
	public PaintBrushToolUI(PaintBrushTool tool) {
		setLayout(new GridLayout(0,1));
		
		// top level panel
		JPanel color_chooser_panel = new JPanel();
		color_chooser_panel.setLayout(new BorderLayout());
		
		// five sliders
		JPanel slider_panel = new JPanel();
		slider_panel.setLayout(new GridLayout(0,1));
		
		JPanel red_slider_panel = new JPanel();
		JLabel red_label = new JLabel("Red:");
		red_slider_panel.setLayout(new BorderLayout());
		red_slider_panel.add(red_label, BorderLayout.WEST);
		red_slider = new JSlider(0,100);
		red_slider.addChangeListener(tool);
		red_slider_panel.add(red_slider, BorderLayout.CENTER);

		JPanel green_slider_panel = new JPanel();
		JLabel green_label = new JLabel("Green:");
		green_slider_panel.setLayout(new BorderLayout());
		green_slider_panel.add(green_label, BorderLayout.WEST);
		green_slider = new JSlider(0,100);
		green_slider.addChangeListener(tool);
		green_slider_panel.add(green_slider, BorderLayout.CENTER);

		JPanel blue_slider_panel = new JPanel();
		JLabel blue_label = new JLabel("Blue: ");
		blue_slider_panel.setLayout(new BorderLayout());
		blue_slider_panel.add(blue_label, BorderLayout.WEST);
		blue_slider = new JSlider(0,100);
		blue_slider.addChangeListener(tool);
		blue_slider_panel.add(blue_slider, BorderLayout.CENTER);

		JPanel size_slider_panel = new JPanel();
		JLabel size_label = new JLabel("Size: ");
		size_slider_panel.setLayout(new BorderLayout());
		size_slider_panel.add(size_label, BorderLayout.WEST);
		size_slider = new JSlider(1,10,5);
		size_slider.setPaintTicks(true);
		size_slider.setSnapToTicks(true);
		size_slider.setPaintLabels(true);
		size_slider.setMajorTickSpacing(1);
		size_slider.addChangeListener(tool);
		size_slider_panel.add(size_slider, BorderLayout.CENTER);
		
		JPanel opacity_slider_panel = new JPanel();
		JLabel opacity_label = new JLabel("Opacity Percent: ");
		opacity_slider_panel.setLayout(new BorderLayout());
		opacity_slider_panel.add(opacity_label, BorderLayout.WEST);
		opacity_slider = new JSlider(0,100,0);
		opacity_slider.setPaintTicks(true);
		opacity_slider.setPaintLabels(true);
		opacity_slider.setMajorTickSpacing(20);
		opacity_slider.setMinorTickSpacing(5);
		opacity_slider.addChangeListener(tool);
		opacity_slider_panel.add(opacity_slider, BorderLayout.CENTER);
		
		// Assumes green label is widest and asks red and blue label
		// to be the same.
		Dimension d = green_label.getPreferredSize();
		red_label.setPreferredSize(d);
		blue_label.setPreferredSize(d);
		size_label.setPreferredSize(d);
		
		slider_panel.add(red_slider_panel);
		slider_panel.add(green_slider_panel);
		slider_panel.add(blue_slider_panel);	
		slider_panel.add(size_slider_panel);
		slider_panel.add(opacity_slider_panel);	
		
		color_chooser_panel.add(slider_panel, BorderLayout.CENTER);

		// preview of the color
		color_preview = new PictureView(Picture.createSolidPicture(50,50, Pixel.WHITE, true).createObservable());
		color_chooser_panel.add(color_preview, BorderLayout.EAST);
		
		// two buttons
		JPanel shapePane=new JPanel();
		shapePane.setLayout(new FlowLayout());
		ButtonGroup shape = new ButtonGroup();
		JRadioButton rectangle = new JRadioButton("rectangle");
		rectangle.setActionCommand("rectangle");
		rectangle.setSelected(true);
		rectangle.addActionListener(tool);
		
		JRadioButton circle = new JRadioButton("circle");
		circle.setActionCommand("circle");
		circle.addActionListener(tool);
		shape.add(rectangle);
		shape.add(circle);
		shapePane.add(rectangle);
		shapePane.add(circle);
		
		color_chooser_panel.add(shapePane,BorderLayout.SOUTH);
		add(color_chooser_panel);
		
	}
	
	// get the RGB value of the sliders
	public Pixel GetColor() {
		return new ColorPixel(red_slider.getValue()/100.0,
				                 green_slider.getValue()/100.0,
				                 blue_slider.getValue()/100.0);
	}
	
	// set the RGB value of the color to sliders
	public void setColor(Pixel p) {
		red_slider.setValue((int)(p.getRed()*100));
		green_slider.setValue((int)(p.getGreen()*100));
		blue_slider.setValue((int)(p.getBlue()*100));
		setPreview(p);
	}
	
	// paint the color preview section
	private void setPreview(Pixel p) {
		ObservablePicture preview_frame = color_preview.getPicture();
		preview_frame.suspendObservable();
		for (int x=0; x<preview_frame.getWidth(); x++) {
			for (int y=0; y<preview_frame.getHeight(); y++) {
				preview_frame.paint(x, y, p);
			}
		}
		preview_frame.resumeObservable();
	}

	public int getBrushSize() {
		return size_slider.getValue();
	}

	public void setBrushSize(int size) {
		size_slider.setValue(size);
	}
	
	public int getOpacityValue() {
		return opacity_slider.getValue();
	}
	
	public void setOpacityValue(int value) {
		opacity_slider.setValue(value);
	}
}
