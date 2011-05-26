package com.creativeward.tabby.commands;

import java.util.List;

import org.eclipse.ui.IWorkbenchPartReference;

public class NextTabHandler extends AbstractTabSwitchHandler {

	protected IWorkbenchPartReference partToActivate(List<IWorkbenchPartReference> partReferences) {
		IWorkbenchPartReference activePartReference = activePage().getActivePartReference();

		partReferences.remove(activePartReference);
		partReferences.add(0, activePartReference);
		
		if(partReferences.size() > 1)
			return partReferences.get(1);
		
		return partReferences.get(0);
	}

}
