package org.abapgit.adt.ui.wizards;

import java.lang.reflect.InvocationTargetException;

import org.abapgit.adt.ui.AbapGitUIPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.statushandlers.StatusManager;

public class WizardPageOne extends WizardPage {
	private Text txtURL;
//	List<IRepository> repos;

	public WizardPageOne() {
		super("First Page");
		setTitle("Git Repositroty Url");
		setDescription("Enter Git Repository Url");

	}

	@Override
	public void createControl(Composite parent) {

//		repos = new LinkedList<>();

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
			public void keyReleased(KeyEvent e) {
				setPageComplete(false);
				setMessage("", NONE);
				if (!txtURL.getText().isEmpty() && txtURL.getText().startsWith("https://")
						&& txtURL.getText().endsWith(".git")) {

					setMessage("Repository can be used", INFORMATION);
					setPageComplete(true);

//					 if (repos.toString().contains(txtURL.getText())) {
//						setMessage("This repository is already in use", WARNING);
//						setPageComplete(false);
//					 }
				}
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO tbd
			}

		});
		txtURL.setLayoutData(gd);

		// required to avoid an error in the system
		setControl(container);
		setPageComplete(false);

	}

	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);

		try {
			getContainer().run(true, true, new IRunnableWithProgress() {

				@Override
				public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
					// TODO tbd
				}
			});
		} catch (InvocationTargetException e) {
			StatusManager.getManager().handle(new Status(IStatus.ERROR, AbapGitUIPlugin.PLUGIN_ID,
					"Error fetching repositories", e.getTargetException()), StatusManager.SHOW);
		} catch (InterruptedException e) {
		}
	}

	public String getTxtUrl() {
		return txtURL.getText();
	}

	public Boolean getRepoPrivate() {
		return true;

	}

}
