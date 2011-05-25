package com.creativeward.tabby.ui.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

public class LineWidget extends Canvas {
	private static final int MIN_SIZE = 10;
	public static final int HORIZONTAL = 100;
	public static final int VERTICAL = 200;
	private final LineDirection direction;
	private final int thickness;
	private final int padding;
	private final int length;

	public LineWidget(Composite parent, LineDirection direction, int length, int thickness, int padding) {
		super(parent, SWT.DOUBLE_BUFFERED);
		this.direction = direction;
		this.length = length;
		this.thickness = thickness;
		this.padding = padding;
		
		initialize();
	}

	private void initialize() {
		addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				GC gc = e.gc;
				Point size = getSize();
				int x, y, width, height;
				int availableWidth = size.x;
				int availablleHeight = size.y;
				
				int totalPadding = padding * 2;
				if(direction == LineDirection.Horizontal) {
					x = padding;
					y = 0;
					width = availableWidth - totalPadding;
					height = thickness;
				} else /*if(direction == LineDirection.Vertical)*/ {
					x = 0;
					y = padding;
					width = thickness;
					height = availablleHeight - totalPadding;
				}

				Color defaultBackground = getBackground();
				gc.setBackground(getForeground());
				gc.fillRectangle(x, y, width, height);
				gc.setBackground(defaultBackground);
			}
		});
	}

	public Point computeSize(int wHint, int hHint, boolean changed) {
		if(direction == LineDirection.Horizontal)
			return new Point(length, Math.max(thickness, MIN_SIZE));
		else 
			return new Point(Math.max(thickness, MIN_SIZE), length);
	}
	
	public enum LineDirection { Horizontal, Vertical }	
}
