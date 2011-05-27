package com.creativeward.tabby.ui.notifications;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.ui.IWorkbenchPartReference;

import com.creativeward.tabby.ui.widgets.TabListWidget;

public class SelectionTransitionManager {
	SelectionTransition FIRST_TO_SECOND;
	SelectionTransition SECOND_TO_FIRST;
	SelectionTransition currentTransition = null;
	List<SelectionTransition> transitions = new ArrayList<SelectionTransition>();
	
	public SelectionTransitionManager(IWorkbenchPartReference activePart, TabListWidget... listWidgets) {
		initializeTransitionCycle(listWidgets);
		initializeCurrentTransition(activePart);
	}

	private void initializeTransitionCycle(TabListWidget... listWidgets) {
		SelectionTransition previousTransition = null;
		for (int i = 0; i < listWidgets.length; i++) {
			SelectionTransition transition = new SelectionTransition(listWidgets[i]);
			if(previousTransition != null)
				transition.setPrevious(previousTransition);
			previousTransition = transition;
			transitions.add(transition);
		}
		
		if(!transitions.isEmpty())
			transitions.get(0).setPrevious(transitions.get(transitions.size() - 1));
	}

	private void initializeCurrentTransition(IWorkbenchPartReference activePart) {
		currentTransition = findTransitionContaining(activePart);
		
		if(currentTransition == null)
			currentTransition = findFirstNonEmptyTransition();
		
		if(currentTransition == null)
			currentTransition = transitions.get(0);

		currentTransition.select(activePart);
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

	private void switchTransition(SelectionTransition nextTransition) {
		currentTransition = nextTransition;
	}

	private SelectionTransition findTransitionContaining(IWorkbenchPartReference activePart) {
		for (SelectionTransition transition : transitions) {
			if(transition.has(activePart)) {
				return transition;
			}
		}
		return null;
	}
	
	private SelectionTransition findFirstNonEmptyTransition() {
		for (SelectionTransition transition : transitions) {
			if(!transition.isEmpty())
				return transition;
		}
		return null;
	}

	class SelectionTransition implements SelectionRolloverListener{
		private TabListWidget listWidget;
		private SelectionTransition next;
		private SelectionTransition previous;

		public SelectionTransition(TabListWidget listWidget) {
			this.listWidget = listWidget;
			listWidget.addSelectionRolloverListener(this);
		}

		public void select(IWorkbenchPartReference toSelect) {
			listWidget.select(toSelect);
		}

		public boolean has(IWorkbenchPartReference activePart) {
			return listWidget.has(activePart);
		}

		public boolean isEmpty() {
			return listWidget.isEmpty();
		}

		public void selectPrevious() {
			listWidget.selectPrevious();
		}

		public void selectNext() {
			listWidget.selectNext();
		}

		public IWorkbenchPartReference selection() {
			return listWidget.selection();
		}

		public void setNext(SelectionTransition nextTransition) {
			next = nextTransition;
			nextTransition.previous = this;
		}
		
		public void setPrevious(SelectionTransition previousTransition) {
			previous = previousTransition;
			previousTransition.next = this;
		}
		
		public boolean selectionRollingOver(SelectionRolloverDirection direction) {
			rolloverSelection(direction, listWidget, next.listWidget, previous.listWidget);
			return true;
		}

		private void rolloverSelection(SelectionRolloverDirection direction, TabListWidget currentTabList, TabListWidget nextTabList, TabListWidget previousTabList) {
			currentTabList.clearSelection();
			if(direction == SelectionRolloverDirection.Bottom) {
				switchTransition(next);
				nextTabList.selectFirst();
			} else {
				switchTransition(previous);
				previousTabList.selectLast();
			}
		}
	}
}
