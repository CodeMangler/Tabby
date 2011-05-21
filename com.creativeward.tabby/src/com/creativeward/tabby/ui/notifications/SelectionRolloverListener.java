package com.creativeward.tabby.ui.notifications;

public interface SelectionRolloverListener {

	public boolean selectionRollingOver(SelectionRolloverDirection direction);

	public enum SelectionRolloverDirection { Top, Bottom }

}
