package com.creativeward.tabby.ui.widgets;

import static com.creativeward.tabby.tests.utils.TabbyTestUtils.workbenchPartReference;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class TabListItemWidgetSample {
	public static void main(String[] args) {
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setLayout(new GridLayout(1, true));
		shell.setBackground(display.getSystemColor(SWT.COLOR_RED));
		
		Composite composite = new Composite(shell, SWT.NONE);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		composite.setLayout(new GridLayout(1, true));
		
		TabListItemWidget listItemOne = new TabListItemWidget(composite, workbenchPartReference("A Part"));
		GridData layoutData = new GridData(SWT.FILL, SWT.CENTER, true, false);
		listItemOne.setLayoutData(layoutData);
		
		TabListItemWidget listItemTwo = new TabListItemWidget(composite, workbenchPartReference("Another Part"));
		listItemTwo.setLayoutData(layoutData);
		listItemTwo.select();

		shell.pack();
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}
}
