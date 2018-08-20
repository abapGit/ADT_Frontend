package org.abapgit.adt.ui.wizards;

import org.eclipse.jface.dialogs.DialogPage;
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

	public WizardPageOne() {
		super("First Page");
		setTitle("Git repositroty url");
		setDescription("Please define git url and package");
	}

	@Override
	public void createControl(Composite parent) {

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
				// do nothing
			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (!txtURL.getText().isEmpty() && txtURL.getText().startsWith("https://")
						&& txtURL.getText().endsWith(".git")) {
					setMessage(null);
					setPageComplete(true);
				} else {
					setMessage("Please enter a valid git URL.", DialogPage.INFORMATION);
					setPageComplete(false);
				}
			}

		});
		txtURL.setLayoutData(gd);

		setControl(container);
		setPageComplete(false);
	}

	public String getTxtUrl() {
		return txtURL.getText();
	}

}
