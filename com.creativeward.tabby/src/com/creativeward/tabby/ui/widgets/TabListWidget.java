package com.creativeward.tabby.ui.widgets;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbenchPartReference;

import com.creativeward.tabby.ui.notifications.SelectionRolloverListener;
import com.creativeward.tabby.ui.notifications.SelectionRolloverListener.SelectionRolloverDirection;

public class TabListWidget extends Composite {
	private final List<IWorkbenchPartReference> parts;
	private final List<TabListItemWidget> listItems = new ArrayList<TabListItemWidget>();
	private final List<SelectionRolloverListener> selectionRolloverListeners = new ArrayList<SelectionRolloverListener>();
	private TabListItemWidget selectedItem = null;
	private int selectedIndex = -1;
	
	public TabListWidget(Composite parent, String title, List<IWorkbenchPartReference> workbenchParts) {
		super(parent, SWT.DOUBLE_BUFFERED);
		this.parts = workbenchParts;
		
		initialize();
	}

	private void initialize() {
		setLayout(new GridLayout(1, true));
		
		for (IWorkbenchPartReference part : parts) {
			TabListItemWidget listItem = new TabListItemWidget(this, part);
			listItem.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
			listItems.add(listItem);
		}		
	}

	public void selectFirst() {
		if(listItems.isEmpty())
			return;
		clearSelection();
		
		selectedIndex = 0;
		selectedItem = listItems.get(selectedIndex);
		selectedItem.select();
	}

	public void selectLast() {
		if(listItems.isEmpty())
			return;
		clearSelection();
		
		selectedIndex = listItems.size() - 1;
		selectedItem = listItems.get(selectedIndex);
		selectedItem.select();
	}

	public void select(IWorkbenchPartReference toSelect) {
		if(toSelect == null)
			return;
		
		clearSelection();
		
		for (int i = 0; i < listItems.size(); i++) {
			TabListItemWidget listItem = listItems.get(i);
			if(listItem.has(toSelect)) {
				selectedIndex = i;
				if(selectedItem != null)
					selectedItem.deselect();
				
				selectedItem = listItem;
				selectedItem.select();
				return;
			}			
		}
	}

	public void selectNext() {
		selectedIndex++;
		if(selectedItem != null)
			selectedItem.deselect();
		
		if(selectedIndex >= listItems.size()) {
			boolean selectionRolloverHandled = handleSelectionRollover(SelectionRolloverDirection.Bottom);
			if(selectionRolloverHandled)
				return;
			else
				selectedIndex = 0;
		}
		
		selectedItem = listItems.get(selectedIndex);
		selectedItem.select();
	}

	public void selectPrevious() {
		selectedIndex--;
		
		if(selectedItem != null)
			selectedItem.deselect();
		
		if(selectedIndex < 0) {
			boolean selectionRolloverHandled = handleSelectionRollover(SelectionRolloverDirection.Top);
			if(selectionRolloverHandled)
				return;
			else
				selectedIndex = listItems.size() - 1;
		}

		selectedItem = listItems.get(selectedIndex);
		selectedItem.select();
	}
	
	public void clearSelection() {
		selectedIndex = -1;
		if(selectedItem != null)
			selectedItem.deselect();
		selectedItem = null;
	}
	
	public IWorkbenchPartReference selection() {
		if(selectedItem == null)
			return null;
		return selectedItem.workbenchPartReference();
	}
	
	public boolean has(IWorkbenchPartReference element) {
		return parts.contains(element);
	}
	
	public boolean isEmpty() {
		return listItems.isEmpty();
	}
	
	public void addSelectionRolloverListener(SelectionRolloverListener listener) {
		if(listener != null)
			selectionRolloverListeners.add(listener);
	}
	
	private boolean handleSelectionRollover(SelectionRolloverDirection direction) {
		for (SelectionRolloverListener listener : selectionRolloverListeners) {
			if(listener.selectionRollingOver(direction))
				return true;
		}
		return false;
	}

	public void dispose() {
		for (TabListItemWidget listItem : listItems) {
			if(!listItem.isDisposed())
				listItem.dispose();
		}
		super.dispose();
	}
}
