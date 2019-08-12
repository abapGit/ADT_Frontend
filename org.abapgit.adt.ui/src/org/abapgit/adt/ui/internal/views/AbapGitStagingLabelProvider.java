package org.abapgit.adt.ui.internal.views;

import java.util.Locale;

import org.abapgit.adt.backend.model.abapgitstaging.IAbapGitFile;
import org.abapgit.adt.backend.model.abapgitstaging.IAbapGitObject;
import org.abapgit.adt.backend.model.abapgitstaging.IIgnoredObjects;
import org.abapgit.adt.ui.internal.i18n.Messages;
import org.eclipse.jface.viewers.StyledCellLabelProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.ViewerCell;

import com.sap.adt.util.ui.viewers.ColorStyler.GrayStyler;

public class AbapGitStagingLabelProvider extends StyledCellLabelProvider {

	private final static String DASH = " - "; //$NON-NLS-1$

	@Override
	public String getToolTipText(Object element) {
		if (element instanceof IAbapGitObject) {
			IAbapGitObject object = (IAbapGitObject) element;
			if (object.eContainer() instanceof IIgnoredObjects) {
				return Messages.AbapGitStaging_ignored_objects_xtol;
			}
		}
		return super.getToolTipText(element);
	}

	@Override
	public void update(ViewerCell cell) {
		GrayStyler grayStyler = new GrayStyler();
		StyledString text = new StyledString();

		if (cell.getElement() instanceof IAbapGitObject) {
			IAbapGitObject object = (IAbapGitObject) cell.getElement();
			if (object.eContainer() instanceof IIgnoredObjects) { //Ignored Objects will be shown as greyed out
				text.append(object.getName().toUpperCase(Locale.ENGLISH), grayStyler);
			} else {
				text.append(object.getName().toUpperCase(Locale.ENGLISH));
			}

			//append the object type
			if (object.getType() != null) {
				text.append(DASH, grayStyler).append(object.getType(), grayStyler);
			}
			cell.setText(text.getString());
			cell.setStyleRanges(text.getStyleRanges());

			//TODO: image
		} else if (cell.getElement() instanceof IAbapGitFile) {
			IAbapGitFile file = (IAbapGitFile) cell.getElement();
			if (file.eContainer().eContainer() instanceof IIgnoredObjects) { //Ignored files will be shown as greyed out
				text.append(file.getName().toLowerCase(Locale.ENGLISH), grayStyler);
			} else {
				text.append(file.getName().toLowerCase(Locale.ENGLISH));
			}
			text.append(DASH, grayStyler).append(file.getPath(), grayStyler);
			cell.setText(text.getString());
			cell.setStyleRanges(text.getStyleRanges());

			//TODO: image
		}
	}

}
