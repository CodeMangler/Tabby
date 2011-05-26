package com.creativeward.tabby.history;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartReference;

public class ActivationHistory {

	List<IWorkbenchPart> activationOrder = new ArrayList<IWorkbenchPart>();
	
	public void recordActivation(IWorkbenchPart part) {
		remove(part);
		activationOrder.add(0, part);
	}

	public void remove(IWorkbenchPart part) {
		activationOrder.remove(part);
	}
	
	public List<IWorkbenchPartReference> partsInActivationOrder(List<IWorkbenchPartReference> editorReferences) {
		ArrayList<IWorkbenchPartReference> results = new ArrayList<IWorkbenchPartReference>();
		
		for (IWorkbenchPart part : activationOrder) {
			IWorkbenchPartReference matchingPartReference = null;
			for (IWorkbenchPartReference partReference : editorReferences) {
				if(partReference.getPart(false) == part) {
					results.add(partReference);
					matchingPartReference = partReference;
					break;
				}
			}
			
			if(matchingPartReference != null)
				editorReferences.remove(matchingPartReference);
		}
		
		results.addAll(editorReferences);
		
		return results;
	}
}
