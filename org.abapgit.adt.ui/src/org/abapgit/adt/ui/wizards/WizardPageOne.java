package org.abapgit.adt.ui.wizards;

import java.util.List;

import org.abapgit.adt.Repository;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class WizardPageOne extends WizardPage {
	private Text txtURL;
	private Composite container;
	private List<Repository> avRepos;

	public WizardPageOne() {
		super("First Page");
		setTitle("Git repositroty url");
		setDescription("Please define git url and package");
	}

	@Override
	public void createControl(Composite parent) {

		avRepos = Repository.list();
		container = new Composite(parent, SWT.NONE);
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

	public String getTxtUrl() {

		return txtURL.getText();
	}
	
	public Boolean getRepoPrivate() {
		return true;
		
	}
	

}
