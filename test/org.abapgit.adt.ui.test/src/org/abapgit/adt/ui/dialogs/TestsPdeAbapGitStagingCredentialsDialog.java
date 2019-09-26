package org.abapgit.adt.ui.dialogs;

import org.abapgit.adt.backend.IExternalRepositoryInfoRequest;
import org.abapgit.adt.ui.internal.i18n.Messages;
import org.abapgit.adt.ui.internal.views.AbapGitStagingView;
import org.abapgit.adt.ui.internal.views.TestsPdeAbapGitStagingUtil;
import org.eclipse.core.runtime.CoreException;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestsPdeAbapGitStagingCredentialsDialog {
	
	private static AbapGitStagingView view;
	private static TestsPdeAbapGitStagingUtil testUtil;
	
	@BeforeClass
	public static void setUp() throws CoreException{
		testUtil = new TestsPdeAbapGitStagingUtil();
		view = testUtil.initializeView();
	}
	
	@AfterClass
	public static void disposeControls() throws CoreException {
		view.dispose();
	}
	
	@Test
	public void credentialsDialog() {
		AbapGitStagingCredentialsDialog dialog = new AbapGitStagingCredentialsDialog(view.getSite().getShell());
		dialog.setBlockOnOpen(false);
		dialog.open();
		
		dialog.usernameField.setText("");
		dialog.passwordField.setText("");
		
		dialog.okPressed();
		Assert.assertEquals(dialog.getMessage(), Messages.AbapGitStaging_credentials_dialog_error_invalid_username);
		
		dialog.usernameField.setText("lorem_ipsum");
		dialog.okPressed();
		Assert.assertEquals(dialog.getMessage(), Messages.AbapGitStaging_credentials_dialog_error_invalid_password);
		
		dialog.passwordField.setText("lorem_ipsum");
		dialog.okPressed();
		
		IExternalRepositoryInfoRequest externalInfo = dialog.getExternalRepoInfo();
		Assert.assertEquals(externalInfo.getUser(), "lorem_ipsum");
		Assert.assertEquals(externalInfo.getPassword(), "lorem_ipsum");	
	}

}
