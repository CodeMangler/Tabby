package com.creativeward.tabby.tests.utils;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.internal.PartPane;
import org.eclipse.ui.internal.WorkbenchPartReference;

public class TabbyTestUtils {
	public static List<IWorkbenchPartReference> workbenchParts(String... titles) {
		ArrayList<IWorkbenchPartReference> results = new ArrayList<IWorkbenchPartReference>();
		for (String title : titles) {
			results.add(workbenchPartReference(title));
		}
		return results;
	}
	
	public static IWorkbenchPartReference workbenchPartReference(final String title) {
		return new WorkbenchPartReference() {
			public IWorkbenchPage getPage() {
				return null;
			}
			
			protected IWorkbenchPart createPart() {
				return null;
			}
			
			protected PartPane createPane() {
				return null;
			}
			
			public String getTitle() {
				return title;
			}
		};
	}
}
