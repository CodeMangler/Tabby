package com.creativeward.tabby.ui.widgets;

import static com.creativeward.tabby.tests.utils.TabbyTestUtils.workbenchPartReference;
import static com.creativeward.tabby.tests.utils.TabbyTestUtils.workbenchParts;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class TabsWidgetSample {
	public static void main(String[] args) {
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setLayout(new GridLayout(1, false));

		Composite composite = new Composite(shell, SWT.NONE);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		composite.setLayout(new GridLayout(1, true));
		
		TabsWidget tabsWidget = new TabsWidget(composite, 
				workbenchParts("Editor One", "Editor Two", "Editor Three"), 
				workbenchParts("View One", "View Two", "View Three"),
				workbenchPartReference("Editor One"));
		tabsWidget.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		shell.pack();
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}
}
