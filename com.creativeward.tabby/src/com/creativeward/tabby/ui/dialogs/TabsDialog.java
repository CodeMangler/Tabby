package com.creativeward.tabby.ui.dialogs;

import java.util.List;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPartReference;

import com.creativeward.tabby.ui.notifications.TabSelectionListener;
import com.creativeward.tabby.ui.widgets.TabsWidget;

public class TabsDialog extends Dialog {

	private final List<IWorkbenchPartReference> editors;
	private final List<IWorkbenchPartReference> views;
	private TabsWidget tabsWidget;
	private IWorkbenchPartReference selectedPart = null;
	private final IWorkbenchPartReference activePart;
	private final InitialListSelectionDirection initialDirection;

	public TabsDialog(Shell parent, List<IWorkbenchPartReference> editors, List<IWorkbenchPartReference> views, IWorkbenchPartReference activePart, InitialListSelectionDirection initialDirection) {
		super(parent);
		this.editors = editors;
		this.views = views;
		this.activePart = activePart;
		this.initialDirection = initialDirection;
		setShellStyle(SWT.NO_TRIM);
	}

	protected Control createButtonBar(Composite parent) {
		return parent;
	}
	
	protected Control createDialogArea(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		GridData layoutData = new GridData(SWT.FILL, SWT.FILL, true, true);
		layoutData.verticalIndent = -1000; // Anything over -10.. To make sure the ghost of the title bar (or whatever) is hidden..
		composite.setLayoutData(layoutData);
		composite.setLayout(new GridLayout(1, false));
		
		tabsWidget = new TabsWidget(parent, editors, views, activePart);
		tabsWidget.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		initializeListeners();
		
		if(initialDirection == InitialListSelectionDirection.Next)
			tabsWidget.selectNext();
		else
			tabsWidget.selectPrevious();
		
		return composite;
	}

	private void initializeListeners() {
		tabsWidget.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
				close();
			}
		});
		
		tabsWidget.addTabSelectionListener(new TabSelectionListener() {
			public void tabSelected(IWorkbenchPartReference workbenchPartReference) {
				selectedPart = workbenchPartReference;
				close();
			}
		});		
	}

	public IWorkbenchPartReference selection() {
		return selectedPart;
	}
	
	// TODO: Remove the enum, and make the commands return the proper workbenchpartreference to select, and in the proper order (from history)..
	public enum InitialListSelectionDirection { Next, Previous }
}
