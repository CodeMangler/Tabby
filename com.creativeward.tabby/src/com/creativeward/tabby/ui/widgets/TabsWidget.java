package com.creativeward.tabby.ui.widgets;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.ui.IWorkbenchPartReference;

import com.creativeward.tabby.ui.notifications.TabSelectionListener;
import com.creativeward.tabby.ui.widgets.LineWidget.LineDirection;

public class TabsWidget extends Composite {

	private final List<IWorkbenchPartReference> editors;
	private final List<IWorkbenchPartReference> views;
	private TabListWidget viewsList;
	private TabListWidget editorsList;
	private SelectionTransitionManager selectionManager;
	private List<TabSelectionListener> selectionListeners = new ArrayList<TabSelectionListener>();

	public TabsWidget(Composite parent, List<IWorkbenchPartReference> editors, List<IWorkbenchPartReference> views) {
		super(parent, SWT.NONE);
		this.editors = editors;
		this.views = views;
		
		initializeControls(parent);
	}

	private void initializeControls(Composite parent) {
		setLayout(new GridLayout(3, false));
		
		viewsList = new TabListWidget(this, "Views", views);
		viewsList.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		LineWidget line = new LineWidget(this, LineDirection.Vertical, 50, 2, 10);
		line.setLayoutData(new GridData(SWT.CENTER, SWT.FILL, false, true));

		editorsList = new TabListWidget(this, "Editors", editors);
		editorsList.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		initializeListeners();
		
		selectionManager = new SelectionTransitionManager(editorsList, viewsList);
	}

	private void initializeListeners() {
		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if(isCtrlTabPressed(e))
					selectionManager.selectNext();
				
				if(isCtrlShiftTabPressed(e))
					selectionManager.selectPrevious();
			}
		});
		
		addControlListener(new ControlListener() {
			public void controlResized(ControlEvent e) {
				layout(true);
			}
			
			public void controlMoved(ControlEvent e) {
				layout(true);
			}
		});

		Display.getDefault().addFilter(SWT.KeyUp, new Listener() {
			public void handleEvent(Event e) {
				if(e.keyCode == SWT.CTRL) // Control key released
					notifySelection(selectionManager.selection());
			}
		});

	}
	
	public void addTabSelectionListener(TabSelectionListener listener) {
		if(listener != null)
			selectionListeners.add(listener);
	}
	
	protected void notifySelection(IWorkbenchPartReference selection) {
		for (TabSelectionListener listener : selectionListeners) {
			listener.tabSelected(selection);
		}
	}

	private boolean isCtrlTabPressed(KeyEvent e) {
		return e.character == SWT.TAB && ((e.stateMask & SWT.CTRL) != 0) && ((e.stateMask & SWT.SHIFT) == 0);
	}

	private boolean isCtrlShiftTabPressed(KeyEvent e) {
		return e.character == SWT.TAB && ((e.stateMask & SWT.CTRL) != 0) && ((e.stateMask & SWT.SHIFT) != 0);
	}
}
