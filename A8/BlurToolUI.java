package a8;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;

public class BlurToolUI extends JPanel {


	private JSlider size_slider;
	private JRadioButton rectangle;
	private JRadioButton circle;
	
	public BlurToolUI(BlurTool tool) {
		setLayout(new GridLayout(0,1));
		
		// top level panel
		JPanel choice_panel = new JPanel();
		choice_panel.setLayout(new BorderLayout());
		
		JPanel size_slider_panel = new JPanel();
		JLabel size_label = new JLabel("Size: ");
		size_slider_panel.setLayout(new BorderLayout());
		size_slider_panel.add(size_label, BorderLayout.WEST);
		size_slider = new JSlider(1,20,5);
		size_slider.setPaintTicks(true);
		size_slider.setSnapToTicks(true);
		size_slider.setPaintLabels(true);
		size_slider.setMajorTickSpacing(1);
		size_slider.addChangeListener(tool);
		size_slider_panel.add(size_slider, BorderLayout.CENTER);
		
		choice_panel.add(size_slider_panel,BorderLayout.CENTER);
			
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
		
		choice_panel.add(shapePane,BorderLayout.SOUTH);
		add(choice_panel);
	}
	
	public int getBlurSize() {
		return size_slider.getValue();
	}

	public void setBlurSize(int size) {
		size_slider.setValue(size);
	}
}
