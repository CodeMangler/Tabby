package com.creativeward.tabby.commands;

import java.util.List;

import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPartReference;

import com.creativeward.tabby.ui.dialogs.TabsDialog;
import com.creativeward.tabby.ui.dialogs.TabsDialog.InitialListSelectionDirection;

public class NextTabHandler extends AbstractTabSwitchHandler {

	protected TabsDialog createTabsDialog(Shell shell, List<IWorkbenchPartReference> editors, List<IWorkbenchPartReference> views, IWorkbenchPartReference activePart) {
		return new TabsDialog(shell, editors, views, activePart, InitialListSelectionDirection.Next);
	}

}
