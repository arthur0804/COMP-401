package a8;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ImageEditorView extends JPanel {

	private JFrame main_frame;
	private PictureView frame_view;
	private ImageEditorModel model;
	private ToolChooserWidget chooser_widget;
	private JPanel tool_ui_panel;
	private JPanel tool_ui;
	private JButton undo;
	private JButton redo;
	private JButton open;
	private JTextField url;
	
	public ImageEditorView(JFrame main_frame, ImageEditorModel model) {
		this.main_frame = main_frame;
		this.model = model;
		
		setLayout(new BorderLayout());
		
		frame_view = new PictureView(model.getCurrent());
		
		add(frame_view, BorderLayout.CENTER);
		
		tool_ui_panel = new JPanel();
		tool_ui_panel.setLayout(new BorderLayout());
		
		chooser_widget = new ToolChooserWidget();
		tool_ui_panel.add(chooser_widget, BorderLayout.NORTH);
		
		// new section
		JPanel utility_panel = new JPanel(new GridLayout(2, 1));
		
		// two buttons
		JPanel undo_redo_panel = new JPanel(new GridLayout(1, 2));
		undo = new JButton("Undo");
		redo = new JButton("Redo");
		undo_redo_panel.add(undo);
		undo_redo_panel.add(redo);
		utility_panel.add(undo_redo_panel);
		
		// text field and open button
		JPanel open_panel = new JPanel(new GridLayout(1, 2));
		url = new JTextField();
		open = new JButton("Open");
		open_panel.add(url);
		open_panel.add(open);
		utility_panel.add(open_panel);
		
		tool_ui_panel.add(utility_panel, BorderLayout.SOUTH);
		add(tool_ui_panel, BorderLayout.SOUTH);
		
		tool_ui = null;
	}

	public void addToolChoiceListener(ToolChoiceListener l) {
		chooser_widget.addToolChoiceListener(l);
	}
	
	public String getCurrentToolName() {
		return chooser_widget.getCurrentToolName();
	}

	public void installToolUI(JPanel ui) {
		if (tool_ui != null) {
			tool_ui_panel.remove(tool_ui);
		}
		tool_ui = ui;
		tool_ui_panel.add(tool_ui, BorderLayout.CENTER);
		validate();
		main_frame.pack();
	}
	
	// update view
	public void updateView() {
		frame_view.setPicture(model.getCurrent());
	}
	
	// get URL from the text field
	public String getURL() {
		return url.getText();
	}
	
	// add button action listener
	public void addButtonListener(ActionListener l) {
		undo.addActionListener(l);
		redo.addActionListener(l);
		open.addActionListener(l);
	}
	
	// clean the text field after opening a new picture
	public void rePack() {
		url.setText("");
		main_frame.pack();
	}
	
	@Override
	public void addMouseMotionListener(MouseMotionListener l) {
		frame_view.addMouseMotionListener(l);
	}
	
	@Override
	public void removeMouseMotionListener(MouseMotionListener l) {
		frame_view.removeMouseMotionListener(l);
	}

	@Override
	public void addMouseListener(MouseListener l) {
		frame_view.addMouseListener(l);
	}
	
	@Override
	public void removeMouseListener(MouseListener l) {
		frame_view.removeMouseListener(l);
	}
	

}
