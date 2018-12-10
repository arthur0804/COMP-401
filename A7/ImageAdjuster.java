package a7;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class ImageAdjuster {

	public static void main(String[] args) throws IOException{
		Picture p = A7Helper.readFromURL("http://www.cs.unc.edu/~kmp/kmp-in-namibia.jpg");
		p.setCaption("KMP in Namibia");
		ImageAdjusterWidget AdjusterWidget = new ImageAdjusterWidget(p);
		
		JFrame main_frame = new JFrame();
		main_frame.setTitle("A7 Image Adjuster");
		main_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel top_panel = new JPanel();
		top_panel.setLayout(new BorderLayout());
		top_panel.add(AdjusterWidget, BorderLayout.CENTER);
		main_frame.setContentPane(top_panel);

		main_frame.pack();
		main_frame.setVisible(true);

	}

}
