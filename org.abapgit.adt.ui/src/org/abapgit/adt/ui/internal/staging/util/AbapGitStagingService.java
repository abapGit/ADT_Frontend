package org.abapgit.adt.ui.internal.staging.util;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.abapgit.adt.backend.FileServiceFactory;
import org.abapgit.adt.backend.IFileService;
import org.abapgit.adt.backend.model.abapgitexternalrepo.IExternalRepositoryInfoRequest;
import org.abapgit.adt.backend.model.abapgitstaging.IAbapGitFile;
import org.abapgit.adt.backend.model.abapgitstaging.IAbapGitObject;
import org.abapgit.adt.ui.AbapGitUIPlugin;
import org.abapgit.adt.ui.internal.i18n.Messages;
import org.abapgit.adt.ui.internal.util.AbapGitService;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IEditorRegistry;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.internal.ide.IDEWorkbenchPlugin;

import com.sap.adt.tools.core.AdtObjectReference;
import com.sap.adt.tools.core.IAdtObjectReference;
import com.sap.adt.tools.core.model.util.AdtObjectReferenceAdapterFactory;
import com.sap.adt.tools.core.ui.navigation.AdtNavigationServiceFactory;

public class AbapGitStagingService extends AbapGitService implements IAbapGitStagingService {

	public void openEditor(Object object, IProject project, IExternalRepositoryInfoRequest credentials) {
		if (object instanceof IAbapGitObject) {
			//open the abap object in the native ADT editor
			openAbapObject((IAbapGitObject) object, project);
		} else if (object instanceof IAbapGitFile) {
			//open the abapgit file
			openAbapGitFile((IAbapGitFile) object, project, credentials);
		}
	}

	/**
	 * Utility method for opening the ADT editor for an abap object
	 *
	 * @param abapObject
	 *            Object to be opened
	 * @param project
	 *            Abap project
	 */
	private void openAbapObject(IAbapGitObject abapObject, IProject project) {
		try {
			if (abapObject.getUri() != null && !abapObject.getUri().isEmpty()) {
				URI objectURI = new URI(abapObject.getUri());
				//Use ADT navigation service for opening the native editor for the selected abap object
				IAdtObjectReference ref = new AdtObjectReference(objectURI, abapObject.getName(), abapObject.getWbkey(), null);
				AdtNavigationServiceFactory.createNavigationService().navigate(project,
						AdtObjectReferenceAdapterFactory.createFromNonEmfReference(ref), true);
			}
		} catch (URISyntaxException e) {
			AbapGitUIPlugin.getDefault().getLog().log(new Status(IStatus.ERROR, AbapGitUIPlugin.PLUGIN_ID, e.getMessage(), e));
		}
	}

	/**
	 * Utility method for opening the text editor for abapgit files. All
	 * <i>xml</i> files will be opened in the xml editor if available and the
	 * other source files will be opened in the default text editor.
	 *
	 * @param file
	 *            AbapGit file to be opened
	 * @param project
	 *            Abap project
	 */
	private void openAbapGitFile(IAbapGitFile file, IProject project, IExternalRepositoryInfoRequest credentials) {
		if (!isFetchFileContentSupported(file)) {
			IAbapGitObject abapObject = (IAbapGitObject) file.eContainer();
			if (abapObject.getType() == null) {
				MessageDialog.openInformation(Display.getDefault().getActiveShell(),
						Messages.AbapGitStaging_open_file_editor_not_supported_dialog_title,
						NLS.bind(Messages.AbapGitStaging_open_file_editor_not_supported_xmg, file.getName()));
			} else {
				openAbapObject((IAbapGitObject) file.eContainer(), project);
			}
			return;
		}
		if (!checkIfOpenFileEditorSupportedForObject(file)) {
			MessageDialog.openInformation(Display.getDefault().getActiveShell(),
					Messages.AbapGitStaging_open_file_editor_not_supported_dialog_title,
					NLS.bind(Messages.AbapGitStaging_open_file_editor_not_supported_xmg, file.getName()));
			return;
		}
		Job openEditor = new Job(Messages.AbapGitStaging_open_file_editor_job_title) {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				try {
					String fileContents = FileServiceFactory.createFileService().readLocalFileContents(file, credentials,
							getDestination(project));
					openFileEditor(file, fileContents);
					return Status.OK_STATUS;
				} catch (IOException e) {
					AbapGitUIPlugin.getDefault().getLog().log(new Status(IStatus.ERROR, AbapGitUIPlugin.PLUGIN_ID, e.getMessage(), e));
					return Status.CANCEL_STATUS;
				}
			}
		};
		openEditor.schedule();
	}

	private void openFileEditor(IAbapGitFile file, String fileContents) {
		Display.getDefault().asyncExec(() -> {
			IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
			try {
				IDE.openEditor(page, new AbapGitFileEditorInput(file, fileContents), getEditorId(file.getName()));
			} catch (PartInitException e) {
				AbapGitUIPlugin.getDefault().getLog().log(new Status(IStatus.ERROR, AbapGitUIPlugin.PLUGIN_ID, e.getMessage(), e));
			}
		});
	}

	/**
	 * Checks whether opening the given file type is supported. </br>
	 * Currently under the non-code meta files, only xml files are supported.
	 */
	private boolean checkIfOpenFileEditorSupportedForObject(IAbapGitFile file) {
		IAbapGitObject abapObject = (IAbapGitObject) file.eContainer();
		if (abapObject.getType() == null) { //non code meta files
			if (!file.getName().endsWith("xml")) { //$NON-NLS-1$
				return false;
			}
		}
		return true;
	}

	private String getEditorId(String fileName) {
		if (fileName.endsWith("xml")) { //$NON-NLS-1$
			//if file type is .xml, return the associated XML editor
			IEditorRegistry editorReg = PlatformUI.getWorkbench().getEditorRegistry();
			IEditorDescriptor desc = editorReg.getDefaultEditor(fileName, Platform.getContentTypeManager().findContentTypeFor(fileName));
			if (desc != null) {
				return desc.getId();
			}
		}
		//use eclipse default text editor for all other files
		return IDEWorkbenchPlugin.DEFAULT_TEXT_EDITOR_ID;
	}

	@Override
	public boolean isFileCompareSupported(Object object) {
		return isFetchFileContentSupported(object);
	}

	public boolean isFetchFileContentSupported(Object object) {
		if (object == null) {
			return false;
		}

		IAbapGitFile file;
		if (object instanceof IAbapGitFile) {
			file = (IAbapGitFile) object;
		} else {
			file = ((IAbapGitObject) object).getFiles().get(0);
		}
		//Compare with remote feature is available from 2002 Abap in CP release
		//Check if the necessary atom link is present in the model
		//TODO remove this check once the customer upgrades to 2002 release
		if (getHrfFromAtomLink(file, IFileService.RELATION_FILE_FETCH_LOCAL) != null) {
			return true;
		}
		return false;
	}
}
