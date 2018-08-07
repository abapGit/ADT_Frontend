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


public class WizardPageFour extends WizardPage {
    private Text txtTr;
	
    public WizardPageFour() {
        super("Last Page");
        setTitle("Transport request"); 
        setDescription("Define transport request below");
    }

	@Override
	public void createControl(Composite parent) {
		 
		Composite container = new Composite(parent, SWT.NONE);
        GridLayout layout = new GridLayout();
        GridData gd = new GridData(GridData.FILL_HORIZONTAL);
        container.setLayout(layout);
        layout.numColumns = 2;
		

        /////// URL INPUT 
        Label lblTr = new Label(container, SWT.NONE);
		lblTr.setText("Transport request");

		txtTr = new Text(container, SWT.BORDER | SWT.SINGLE);
		txtTr.setText("");        

		txtTr.addKeyListener(new KeyListener() {

            @Override
            public void keyPressed(KeyEvent e) {
                // TODO Auto-generated method stub
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (!txtTr.getText().isEmpty()) {
                    setPageComplete(true);

                }
            }

        });
        txtTr.setLayoutData(gd); 
        
        // required to avoid an error in the system
        setControl(container);
        setPageComplete(false);
		
	}
	
    public String getTxtTr() {
        return txtTr.getText();
    }

}
