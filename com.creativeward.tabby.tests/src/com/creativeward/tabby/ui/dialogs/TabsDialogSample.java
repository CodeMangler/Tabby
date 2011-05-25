package com.creativeward.tabby.ui.dialogs;

import static com.creativeward.tabby.tests.utils.TabbyTestUtils.workbenchPartReference;
import static com.creativeward.tabby.tests.utils.TabbyTestUtils.workbenchParts;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.creativeward.tabby.ui.dialogs.TabsDialog.InitialListSelectionDirection;


public class TabsDialogSample {
	public static void main(String[] args) {
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setLayout(new GridLayout(1, false));

		new TabsDialog(shell, 
				workbenchParts("Editor One", "Editor Two", "Editor Three"), 
				workbenchParts("View One", "View Two", "View Three"), workbenchPartReference("Editor One"), InitialListSelectionDirection.Next).open();

		shell.setText("Main Shell");
		shell.pack();
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}
}
