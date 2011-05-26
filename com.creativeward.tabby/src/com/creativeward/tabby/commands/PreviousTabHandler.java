package com.creativeward.tabby.commands;

import java.util.List;

import org.eclipse.ui.IWorkbenchPartReference;

public class PreviousTabHandler extends AbstractTabSwitchHandler {

	protected IWorkbenchPartReference partToActivate(List<IWorkbenchPartReference> partReferences) {
		IWorkbenchPartReference activePartReference = activePage().getActivePartReference();

		partReferences.remove(activePartReference);
		partReferences.add(0, activePartReference);
		
		return partReferences.get(partReferences.size() - 1);
	}
	
}
