package com.creativeward.tabby.ui.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.creativeward.tabby.ui.widgets.LineWidget.LineDirection;

public class LineWidgetSample {
	public static void main(String[] args) {
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setLayout(new GridLayout(1, false));

		LineWidget lineOne = new LineWidget(shell, LineDirection.Horizontal, 50, 5, 10);
		lineOne.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		
		LineWidget lineTwo = new LineWidget(shell, LineDirection.Vertical, 50, 5, 10);
		lineTwo.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true));

		shell.pack();
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}
}
