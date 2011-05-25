package com.creativeward.tabby.commands;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import com.creativeward.tabby.ui.dialogs.TabsDialog;

public abstract class AbstractTabSwitchHandler extends AbstractHandler {

	public Object execute(ExecutionEvent event) throws ExecutionException {
		List<IWorkbenchPartReference> editors = new ArrayList<IWorkbenchPartReference>();
		List<IWorkbenchPartReference> views = new ArrayList<IWorkbenchPartReference>();
		enumerateWorkbenchParts(editors, views);
		
		TabsDialog dialog = createTabsDialog(Display.getDefault().getActiveShell(), editors, views, activePart());
		IWorkbenchPartReference partToActivate = null;
		if(dialog.open() == TabsDialog.OK) {
			partToActivate = dialog.selection();
			if(partToActivate == null)
				return null;
			
			activePage().activate(partToActivate.getPart(true));
		}
		
		return partToActivate;
	}

	protected abstract TabsDialog createTabsDialog(Shell shell, List<IWorkbenchPartReference> editors, List<IWorkbenchPartReference> views, IWorkbenchPartReference activePart);

	private IWorkbenchPartReference activePart() {
		return activePage().getActivePartReference();
	}

	private IWorkbenchPage activePage() {
		return PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
	}

	private void enumerateWorkbenchParts(List<IWorkbenchPartReference> editors, List<IWorkbenchPartReference> views) {
		IWorkbenchWindow[] workbenchWindows = PlatformUI.getWorkbench().getWorkbenchWindows();
		for (IWorkbenchWindow workbenchWindow : workbenchWindows) {
			IWorkbenchPage[] pages = workbenchWindow.getPages();
			for (IWorkbenchPage page : pages) {
				IEditorReference[] editorReferences = page.getEditorReferences();
				for (IEditorReference editorReference : editorReferences) {
					editors.add(editorReference);
				}
				
				IViewReference[] viewReferences = page.getViewReferences();
				for (IViewReference viewReference : viewReferences) {
					views.add(viewReference);
				}
			}
		}		
	}
}
