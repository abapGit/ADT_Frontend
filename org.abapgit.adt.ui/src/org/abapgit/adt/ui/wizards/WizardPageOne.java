package org.abapgit.adt.ui.wizards;

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
    
    public WizardPageOne() {
        super("First Page");
        setTitle("First Page");
        setDescription("Please define git url and package");
    }

	@Override
	public void createControl(Composite parent) {
        
		container = new Composite(parent, SWT.NONE);
        GridLayout layout = new GridLayout();
        GridData gd = new GridData(GridData.FILL_HORIZONTAL);
        container.setLayout(layout);
        layout.numColumns = 2;
		

        /////// URL INPUT 
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
                if (!txtURL.getText().isEmpty()) {
                    setPageComplete(true);

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

}
