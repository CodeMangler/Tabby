package com.creativeward.tabby.history;

import org.eclipse.ui.IPageListener;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IWindowListener;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

public class ActivationHistoryTracker implements IWindowListener, IPageListener, IPartListener {

	private ActivationHistory activationHistory;

	public ActivationHistoryTracker() {
		activationHistory = new ActivationHistory();
		
		IWorkbench workbench = PlatformUI.getWorkbench();
		IWorkbenchWindow activeWorkbenchWindow = workbench.getActiveWorkbenchWindow();
		workbench.addWindowListener(this);
		activeWorkbenchWindow.addPageListener(this);
		activeWorkbenchWindow.getPartService().addPartListener(this);
	}
	
	public ActivationHistory getActivationHistory() {
		return activationHistory;
	}
	
// IWindowListener Methods
	public void windowActivated(IWorkbenchWindow window) { }

	public void windowDeactivated(IWorkbenchWindow window) { }

	public void windowClosed(IWorkbenchWindow window) {
		window.removePageListener(this);
	}

	public void windowOpened(IWorkbenchWindow window) {
		window.addPageListener(this);
	}
// IWindowListener Methods

// IPageListener Methods
	public void pageActivated(IWorkbenchPage page) { }

	public void pageClosed(IWorkbenchPage page) {
		page.removePartListener(this);
	}

	public void pageOpened(IWorkbenchPage page) {
		page.addPartListener(this);
	}
// IPageListener Methods

// IPartListener Methods
	public void partActivated(IWorkbenchPart part) {
		activationHistory.recordActivation(part);
	}

	public void partBroughtToTop(IWorkbenchPart part) { 
		activationHistory.recordActivation(part);
	}

	public void partClosed(IWorkbenchPart part) { 
		activationHistory.remove(part);
	}

	public void partDeactivated(IWorkbenchPart part) { }

	public void partOpened(IWorkbenchPart part) {
		activationHistory.recordActivation(part);
	}
// IPartListener Methods
}
