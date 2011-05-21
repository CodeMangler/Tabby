package com.creativeward.tabby.ui.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchPartReference;

public class TabListItemWidget extends Canvas {
	private static final int PADDING = 2;
	private static final int DOUBLE_PADDING = PADDING * 2;
	private static final int IMAGE_TEXT_INTERVAL = 2;
	private static final int ICON_SIZE = 16;
	
	private static final int MIN_WIDTH = DOUBLE_PADDING + IMAGE_TEXT_INTERVAL + ICON_SIZE;
	private static final int MIN_HEIGHT = DOUBLE_PADDING + ICON_SIZE;

	private static final int TEXT_START = PADDING + ICON_SIZE + IMAGE_TEXT_INTERVAL;

	private String text = "";
	private Image icon = null;
	private boolean selected = false;
	private Color defaultBackground;
	private Color selectionBackground;
	private final IWorkbenchPartReference partReference;

	public TabListItemWidget(Composite parent, IWorkbenchPartReference part) {
		super(parent, SWT.DOUBLE_BUFFERED);
		this.partReference = part;
		text = part.getTitle();
		icon = part.getTitleImage();
		defaultBackground = getBackground();
		selectionBackground = parent.getDisplay().getSystemColor(SWT.COLOR_LIST_SELECTION);

		initialize();
	}

	public IWorkbenchPartReference workbenchPartReference() {
		return partReference;
	}
	
	public Point getSize() {
		GC gc = new GC(Display.getCurrent());
		Point textSize = gc.textExtent(text);
		return new Point(MIN_WIDTH + textSize.x, Math.max(MIN_HEIGHT, textSize.y));
	}
	
	public Point computeSize(int wHint, int hHint, boolean changed) {
		return getSize();
	}
	
	private void initialize() {
		setSize(getSize());
		
		addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				GC gc = e.gc;
				
				if(icon != null)
					gc.drawImage(icon, PADDING, PADDING);
				
				gc.drawText(text, TEXT_START, PADDING);
			}
		});
	}
	
	void select() {
		selected = true;
		setBackground(selectionBackground);
		redraw();
	}
	
	void deselect() {
		selected = false;
		setBackground(defaultBackground);
		redraw();
	}
	
	boolean isSelected() {
		return selected;
	}
}
