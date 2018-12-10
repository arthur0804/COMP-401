package a8;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;

public class ImageEditorController implements ActionListener, ToolChoiceListener, MouseListener, MouseMotionListener {

	private ImageEditorView view;
	private ImageEditorModel model;
	private Tool current_tool;
	private PixelInspectorTool inspector_tool;
	private PaintBrushTool paint_brush_tool;
	private BlurTool blur_tool;
	
	public ImageEditorController(ImageEditorView view, ImageEditorModel model) {
		this.view = view;
		this.model = model;

		inspector_tool = new PixelInspectorTool(this);
		paint_brush_tool = new PaintBrushTool(this);
		blur_tool = new BlurTool(this);
		
		view.addToolChoiceListener(this);
		view.addMouseListener(this);
		view.addMouseMotionListener(this);
		view.addButtonListener(this);
		
		this.toolChosen(view.getCurrentToolName());
	}

	@Override
	public void toolChosen(String tool_name) {
		if (tool_name.equals("Pixel Inspector")) {
			view.installToolUI(inspector_tool.getUI());
			current_tool = inspector_tool;
		} else if (tool_name.equals("Paint Brush")) {
			view.installToolUI(paint_brush_tool.getUI());
			current_tool = paint_brush_tool;
		} else if (tool_name.equals("Blur")) {
			view.installToolUI(blur_tool.getUI());
			current_tool = blur_tool;
		}
	}

	public ImageEditorModel getModel() {
		return model;
	}
	
	public void setBrushColor(Pixel p) {
		paint_brush_tool.setBrushColor(p);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		current_tool.mouseClicked(e);
		view.updateView();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		current_tool.mousePressed(e);
		view.updateView();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		current_tool.mouseReleased(e);
		view.updateView();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		current_tool.mouseEntered(e);
		view.updateView();
	}

	@Override
	public void mouseExited(MouseEvent e) {
		current_tool.mouseExited(e);
		view.updateView();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		current_tool.mouseDragged(e);
		view.updateView();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		current_tool.mouseMoved(e);
		view.updateView();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == "Undo") {
			// undo option
			model.undo();
		} else if (e.getActionCommand() == "Redo"){
			// redo option
			model.redo();
		} else {
			// open option
			model.createCopyOfCurrent();
			try {
				model.setPicture(view.getURL());
				view.rePack();
			} catch (IOException ex) {
				System.out.println("Error occurred when trying to open image from url.");
			}
		}
		view.updateView();
	}

}
