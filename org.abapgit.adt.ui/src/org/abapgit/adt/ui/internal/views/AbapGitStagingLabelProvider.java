package org.abapgit.adt.ui.internal.views;

import java.net.URL;
import java.util.Locale;

import org.abapgit.adt.backend.model.abapgitstaging.IAbapGitFile;
import org.abapgit.adt.backend.model.abapgitstaging.IAbapGitObject;
import org.abapgit.adt.backend.model.abapgitstaging.IIgnoredObjects;
import org.abapgit.adt.ui.AbapGitUIPlugin;
import org.abapgit.adt.ui.internal.i18n.Messages;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.DecorationOverlayIcon;
import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.jface.viewers.StyledCellLabelProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.TextStyle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.IFormColors;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.internal.util.BundleUtility;
import org.osgi.framework.Bundle;

import com.sap.adt.tools.core.ui.AbapCoreUi;
import com.sap.adt.tools.core.ui.IAdtObjectTypeInfoUi;
import com.sap.adt.tools.core.ui.IAdtObjectTypeRegistryUi;
import com.sap.adt.util.ui.viewers.ColorStyler;
import com.sap.adt.util.ui.viewers.ColorStyler.GrayStyler;

public class AbapGitStagingLabelProvider extends StyledCellLabelProvider {

	private final static String DASH = " - "; //$NON-NLS-1$

	IAdtObjectTypeRegistryUi objectTypeRegistry = AbapCoreUi.getObjectTypeRegistry();
	GrayStyler grayStyler = new GrayStyler();
	CustomStyler customStyler = new CustomStyler();

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
		if (cell.getElement() instanceof IAbapGitObject) {
			IAbapGitObject object = (IAbapGitObject) cell.getElement();
			updateObjectCell(cell, object);
		} else if (cell.getElement() instanceof IAbapGitFile) {
			IAbapGitFile file = (IAbapGitFile) cell.getElement();
			updateFileCell(cell, file);
		}
	}

	private void updateObjectCell(ViewerCell cell, IAbapGitObject object) {
		StyledString text = new StyledString();
		if (object.getType() == null || object.getType().isEmpty()) { //non-code and meta files
			text.append(object.getName().toLowerCase(Locale.ENGLISH), this.customStyler);
			cell.setImage(PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_FOLDER));
		} else {
			if (object.eContainer() instanceof IIgnoredObjects) { //Ignored Objects will be shown as greyed out
				text.append(object.getName().toUpperCase(Locale.ENGLISH), this.grayStyler);
			} else {
				text.append(object.getName().toUpperCase(Locale.ENGLISH));
			}
			text.append(DASH, this.grayStyler).append(object.getType(), this.grayStyler);
			cell.setImage(getObjectImage(object));
		}

		cell.setText(text.getString());
		cell.setStyleRanges(text.getStyleRanges());
	}

	private Image getObjectImage(IAbapGitObject object) {
		Image objectImage = null;
		IAdtObjectTypeInfoUi objectTypeByGlobalWorkbenchType = this.objectTypeRegistry
				.getObjectTypeByGlobalWorkbenchType(object.getWbkey());
		if (objectTypeByGlobalWorkbenchType != null) {
			objectImage = objectTypeByGlobalWorkbenchType.getImage();
		}
		if (objectImage == null) {
			objectImage = AbapCoreUi.getSharedImages().getImage(com.sap.adt.tools.core.ui.ISharedImages.IMG_OBJECT_VISUAL_INTEGRATED);
		}
		return objectImage;
	}

	private void updateFileCell(ViewerCell cell, IAbapGitFile file) {
		StyledString text = new StyledString();
		if (file.eContainer().eContainer() instanceof IIgnoredObjects) { //Ignored files will be shown as greyed out
			text.append(file.getName().toLowerCase(Locale.ENGLISH), this.grayStyler);
		} else {
			text.append(file.getName().toLowerCase(Locale.ENGLISH));
		}
		text.append(DASH, this.grayStyler).append(file.getPath(), this.grayStyler);
		cell.setText(text.getString());
		cell.setStyleRanges(text.getStyleRanges());
		cell.setImage(getFileImage(file));
	}

	private Image getFileImage(IAbapGitFile file) {
		Image fileImage = PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_FILE);
		if (file.getLocalState().equals(IAbapGitFile.Status.ADDED.getStatus())) { //new file
			fileImage = decorateImage(fileImage, "icons/ovr/added_ovr.png"); //$NON-NLS-1$
		} else if (file.getLocalState().equals(IAbapGitFile.Status.MODIFIED.getStatus())) { //modified file
			fileImage = decorateImage(fileImage, "icons/ovr/dirty_ovr.png"); //$NON-NLS-1$
		} else if (file.getLocalState().equals(IAbapGitFile.Status.DELETED.getStatus())) { //deleted file
			fileImage = decorateImage(fileImage, "icons/ovr/delete_ovr.png"); //$NON-NLS-1$
		}
		return fileImage;
	}

	/**
	 * Method to overlay an image with another
	 */
	private Image decorateImage(Image baseImage, String overlayImagePath) {
		Bundle bundle = Platform.getBundle(AbapGitUIPlugin.PLUGIN_ID);
		URL fullPathString = BundleUtility.find(bundle, overlayImagePath);
		DecorationOverlayIcon doi = new DecorationOverlayIcon(//
				baseImage, ImageDescriptor.createFromURL(fullPathString), IDecoration.BOTTOM_RIGHT);
		return doi.createImage();
	}

	public static final class CustomStyler extends ColorStyler {
		public void applyStyles(TextStyle textStyle) {
			textStyle.foreground = new FormToolkit(Display.getDefault()).getColors().getColor(IFormColors.TB_TOGGLE);
			((StyleRange) textStyle).fontStyle = SWT.ITALIC;
		}
	}

}
