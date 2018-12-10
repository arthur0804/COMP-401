package a7;

import java.awt.*;
import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
 
public class PixelInspectorWidget extends JPanel implements MouseListener, ROIObserver {
    
	private PictureView picture_view;
    
    private JLabel x = new JLabel();
    private JLabel y = new JLabel();
    private JLabel red = new JLabel();
    private JLabel green = new JLabel();
    private JLabel blue = new JLabel();
    private JLabel bright = new JLabel();

    private int cx;
    private int cy;
	
	// validate
    private static Picture checkPicture(Picture p) {
    	if(p == null) {
    		throw new IllegalArgumentException("null picture");
    	}else {
    		return p;
    	}
    }
    
	// Constructor 1
	public PixelInspectorWidget(Picture p) {
		// call constructor 2
		this(new PictureView(checkPicture(p).createObservable()));
		add(picture_view, BorderLayout.EAST);
	}

	// Constructor 2
	public PixelInspectorWidget(PictureView picture_view) {
		setLayout(new BorderLayout());

        // Add picture view
        this.picture_view = picture_view;
        this.picture_view.addMouseListener(this);

        // container of the six labels
        JPanel inspector = new JPanel(new GridLayout(6, 1));
        inspector.setPreferredSize(new Dimension(100, 10));

        // six labels
        JLabel ix = new JLabel("X: ");
        JLabel iy = new JLabel("Y: ");
        JLabel ired = new JLabel("Red: ");
        JLabel igreen = new JLabel("Green: ");
        JLabel iblue = new JLabel("Blue: ");
        JLabel ibright = new JLabel("Brightness: ");

        ix.setHorizontalAlignment(SwingConstants.RIGHT);
        iy.setHorizontalAlignment(SwingConstants.RIGHT);
        ired.setHorizontalAlignment(SwingConstants.RIGHT);
        igreen.setHorizontalAlignment(SwingConstants.RIGHT);
        iblue.setHorizontalAlignment(SwingConstants.RIGHT);
        ibright.setHorizontalAlignment(SwingConstants.RIGHT);

        inspector.add(ix);
        inspector.add(iy);
        inspector.add(ired);
        inspector.add(igreen);
        inspector.add(iblue);
        inspector.add(ibright);

        add(inspector, BorderLayout.WEST);

        // Add fields for data
        JPanel data_panel = new JPanel(new GridLayout(6, 1));
        data_panel.setPreferredSize(new Dimension(50, 10));

        x.setHorizontalAlignment(SwingConstants.LEFT);
        y.setHorizontalAlignment(SwingConstants.LEFT);
        red.setHorizontalAlignment(SwingConstants.LEFT);
        green.setHorizontalAlignment(SwingConstants.LEFT);
        blue.setHorizontalAlignment(SwingConstants.LEFT);
        bright.setHorizontalAlignment(SwingConstants.LEFT);

        update();

        data_panel.add(x);
        data_panel.add(y);
        data_panel.add(red);
        data_panel.add(green);
        data_panel.add(blue);
        data_panel.add(bright);

        add(data_panel, BorderLayout.CENTER);
	}
	
	 private String convert(double d) {
		 return (int) d + "." + (int)(d*100%100);
	 }
	 
	private void update() {
		x.setText("" + cx);
        y.setText("" + cy);
        red.setText("" + convert(picture_view.getPicture().getPixel(cx, cy).getRed()));
        green.setText("" + convert(picture_view.getPicture().getPixel(cx, cy).getGreen()));
        blue.setText("" + convert(picture_view.getPicture().getPixel(cx, cy).getBlue()));
        bright.setText("" + convert(picture_view.getPicture().getPixel(cx, cy).getIntensity()));
		
	}

	@Override
	public void notify(ObservablePicture picture, Region changed_region) {
		update();
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
        cx = e.getX();
        cy = e.getY();

        update();
		
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

}
