package a8;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PixelInspectorUI extends JPanel {

	private JLabel x_label;
	private JLabel y_label;
	private JLabel r_label;
	private JLabel g_label;
	private JLabel b_label;
	private JButton copy;
	private PictureView magnifier;
	
	public PixelInspectorUI(PixelInspectorTool tool) {
		setLayout(new BorderLayout());
		
		JPanel pixel_info = new JPanel(new GridLayout(6, 1));
		x_label = new JLabel("X: ");
		y_label = new JLabel("Y: ");
		r_label = new JLabel("R: ");
		g_label = new JLabel("G: ");
		b_label = new JLabel("B: ");
		
		pixel_info.add(x_label);
		pixel_info.add(y_label);
		pixel_info.add(r_label);
		pixel_info.add(g_label);
		pixel_info.add(b_label);
		
		add(pixel_info, BorderLayout.CENTER);
		
		// initial magnifier: 100*100 pixels
		Pixel[][] parray = new Pixel[100][100];
		for(int i =0;i<100;i++){
			for(int j=0;j<100;j++){
				parray[i][j] = new ColorPixel(1.0,1.0,1.0); 
			}
		}
		Picture magnifier_picture = new MutablePixelArrayPicture(parray, "test");
		magnifier = new PictureView(magnifier_picture.createObservable());
		add(magnifier, BorderLayout.EAST);
		
		copy = new JButton("Copy Color");
		copy.addActionListener(tool);
		add(copy, BorderLayout.SOUTH);
	}
	
	public void setInfo(int x, int y, Pixel p) {
		x_label.setText("X: " + x);
		y_label.setText("Y: " + y);
		r_label.setText(String.format("R: %3.2f", p.getRed()));
		g_label.setText(String.format("G: %3.2f", p.getGreen()));
		b_label.setText(String.format("B: %3.2f", p.getBlue()));		
	}
	
	public void setMagnifier(int x, int y, Pixel p) {
		magnifier.getPicture().paint(x, y, p);
	}

	public void suspendMagnifying() {
		magnifier.getPicture().suspendObservable();
	}

	public void resumeMagnifying() {
		magnifier.getPicture().resumeObservable();
	}
}
