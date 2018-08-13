package org.abapgit.adt.ui.wizards;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.abapgit.adt.backend.AbapGitRequest;
import org.abapgit.adt.backend.Repository;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ITreeSelection;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

public class WizardPageOne extends WizardPage {
	private Text txtURL;
	private List<Repository> avRepos;

	public WizardPageOne() {
		super("First Page");
		setTitle("Git repositroty url");
		setDescription("Please define git url and package");
		

	}

	@Override
	public void createControl(Composite parent) {

		IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		ITreeSelection selection = (ITreeSelection) window.getSelectionService().getSelection(); 
		Shell currShell = super.getShell();
		
		avRepos = new AbapGitRequest(currShell, selection, "").executeGet();
		
		Composite container = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		container.setLayout(layout);
		layout.numColumns = 2;

		Label lblUrl = new Label(container, SWT.NONE);
		lblUrl.setText("URL");

		txtURL = new Text(container, SWT.BORDER | SWT.SINGLE);
		txtURL.setText("");

		txtURL.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent e) {
                // TODO Auto-generated method stub
			}

			@Override
			public void keyReleased(KeyEvent e) {
				setPageComplete(false);
				setMessage("", NONE);
				if (!txtURL.getText().isEmpty() && txtURL.getText().startsWith("https://")
						&& txtURL.getText().endsWith(".git")) {

					setMessage("You can use this repository", INFORMATION);
					setPageComplete(true);

					if (avRepos.toString().contains(txtURL.getText())) {
						setMessage("This repository is already in use", WARNING);
						setPageComplete(false);
					}

				}
			}

		});
		txtURL.setLayoutData(gd);

		// required to avoid an error in the system
		setControl(container);
		setPageComplete(false);

	}
	
	@Override
	public void setVisible(boolean visible) {
		// TODO Auto-generated method stub
		super.setVisible(visible);
		
		
		try {
			getContainer().run(true, true, new IRunnableWithProgress() {
				
				@Override
				public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
					// fetch repos(monitor)
					
				}
			});
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getTxtUrl() {
		return txtURL.getText();
	}
	
	public Boolean getRepoPrivate() {
		return true;
		
	}
	

}
