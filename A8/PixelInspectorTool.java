package a8;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;


public class PixelInspectorTool implements Tool, ActionListener {

	private PixelInspectorUI ui;
	private ImageEditorController controller;
	
	private int cx;
	private int cy;

	private static final Pixel DEFAULT = new ColorPixel(0.5, 0.5, 0.5);
	
	public PixelInspectorTool(ImageEditorController controller) {
		this.controller = controller;
		ui = new PixelInspectorUI(this);
	}

	@Override
	public String getName() {
		return "Pixel Inspector";
	}

	@Override
	public JPanel getUI() {
		return ui;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		try {
			cx = e.getX();
			cy = e.getY();
			ui.setInfo(cx, cy, controller.getModel().getPixel(cx, cy));
			magnify();
		}
		catch (Exception ex) {
			// Click may have been out of bounds. Do nothing in this case.
		}
	}
	
	/*
	 * magnifier preview is defined as 100*100 in PixelInspectorUI
	 * this magnify() method pain 25*25 subsections (each represent a 4*4 section)
	 * and then call the paint() method to paint 4*4 section in each subsections
	 * so there will be 100*100 in total
	 */
	private void magnify() {
		ui.suspendMagnifying();
		Pixel p;
		// 25 columns and 25 rows
		for (int i = -12; i <= 12; i++) {
			for (int j = -12; j <= 12; j++) {
				try {
					p = controller.getModel().getPixel(cx + j, cy + i);
				} catch (Exception e) {
					// may out of boundary
					p = DEFAULT;
				}
				// paint in the 4*4 section
				paint(j*4+48, i*4+48, j*4+51, i*4+51, p);
			}
		}
		// finish painting 100*100 pixels
		ui.resumeMagnifying();
	}
	
	// paint in each 4*4 section
	private void paint(int x1, int y1, int x2, int y2, Pixel p) {
		for (int i = y1; i <= y2; i++) {
			for (int j = x1; j <= x2; j++) {
				ui.setMagnifier(j, i, p);
			}
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		try {
			cx = e.getX();
			cy = e.getY();
			ui.setInfo(cx, cy, controller.getModel().getPixel(cx, cy));
			magnify();
		}
		catch (Exception ex) {
			// Click may have been out of bounds. Do nothing in this case.
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		controller.setBrushColor(controller.getModel().getPixel(cx, cy));	
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
	}
	@Override
	public void mouseClicked(MouseEvent e) {
	}
	@Override
	public void mouseReleased(MouseEvent e) {		
	}
	@Override
	public void mouseEntered(MouseEvent e) {	
	}
	@Override
	public void mouseExited(MouseEvent e) {
	}
}
