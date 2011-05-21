package com.creativeward.tabby.ui.widgets;

import org.eclipse.ui.IWorkbenchPartReference;

import com.creativeward.tabby.ui.notifications.SelectionRolloverListener;

public class SelectionTransitionManager {
	SelectionTransition FIRST_TO_SECOND;
	SelectionTransition SECOND_TO_FIRST;
	SelectionTransition currentTransition;
	
	public SelectionTransitionManager(TabListWidget first, TabListWidget second) {
		FIRST_TO_SECOND = new SelectionTransition(first, second);
		SECOND_TO_FIRST = new SelectionTransition(second, first);
		
		currentTransition = first.hasElements() ? FIRST_TO_SECOND : SECOND_TO_FIRST;
	}

	public IWorkbenchPartReference selection() {
		return currentTransition.selection();
	}

	public void selectNext() {
		currentTransition.selectNext();
	}

	public void selectPrevious() {
		currentTransition.selectPrevious();
	}

	private void switchTransition(SelectionTransition transition) {
		currentTransition = (transition == FIRST_TO_SECOND) ? SECOND_TO_FIRST : FIRST_TO_SECOND;
	}
	
	class SelectionTransition implements SelectionRolloverListener{
		private TabListWidget fromList;
		private TabListWidget toList;

		public SelectionTransition(TabListWidget fromList, TabListWidget toList) {
			this.fromList = fromList;
			this.toList = toList;
			fromList.addSelectionRolloverListener(this);
		}

		public void selectPrevious() {
			fromList.selectPrevious();
		}

		public void selectNext() {
			fromList.selectNext();
		}

		public IWorkbenchPartReference selection() {
			return fromList.selection();
		}

		public boolean selectionRollingOver(SelectionRolloverDirection direction) {
			rolloverToOtherTabList(direction, fromList, toList);
			switchTransition(this);
			return true;
		}

		private void rolloverToOtherTabList(SelectionRolloverDirection direction, TabListWidget fromTabList, TabListWidget toTabList) {
			fromTabList.clearSelection();
			if(direction == SelectionRolloverDirection.Bottom)
				toTabList.selectFirst();
			else
				toTabList.selectLast();
		}
	}
}
