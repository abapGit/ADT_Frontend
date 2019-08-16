package org.abapgit.adt.ui.internal.views;

import java.util.ArrayList;
import java.util.List;

import org.abapgit.adt.backend.IRepository;
import org.abapgit.adt.backend.model.abapgitstaging.IAbapGitFile;
import org.abapgit.adt.backend.model.abapgitstaging.IAbapGitObject;
import org.abapgit.adt.backend.model.abapgitstaging.IAbapGitStaging;
import org.abapgit.adt.ui.AbapGitUIPlugin;
import org.abapgit.adt.ui.internal.i18n.Messages;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.layout.LayoutConstants;
import org.eclipse.jface.layout.RowLayoutFactory;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.text.MarginPainter;
import org.eclipse.jface.text.TextViewer;
import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTargetAdapter;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.Form;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import com.sap.adt.util.ui.SWTUtil;

public class AbapGitStagingView extends ViewPart implements IAbapGitStagingView {

	public static final String VIEW_ID = "org.abapgit.adt.ui.views.AbapGitStagingView"; //$NON-NLS-1$

	private static final int MAX_COMMIT_MESSAGE_LINE_LENGTH = 72;

	private IProject project;
	private IRepository currentRepo;
	private IAbapGitStaging stagingModel;

	//UI controls
	private FormToolkit toolkit;
	private Form mainForm;

	//unstaged section controls
	private Section unstagedSection;
	private TreeViewer unstagedTreeViewer;
	private ToolBarManager unstagedToolbarManager;

	//staged section controls
	private Section stagedSection;
	private TreeViewer stagedTreeViewer;
	private ToolBarManager stagedToolbarManager;

	//commit section controls
	private Section commitSection;
	private Composite warningsComposite;
	private Label warningText;
	private TextViewer commitMessageTextViewer;
	private Text authorText;
	private Text committerText;
	private Button commitAndPushButton;

	//actions
	private IAction actionRefresh;
	private IAction stageAction;
	private IAction unstageAction;

	@Override
	public void createPartControl(Composite parent) {
		createActions();
		//add toolbar actions
		contributeToActionBars();

		GridLayoutFactory.fillDefaults().applyTo(parent);
		this.toolkit = new FormToolkit(parent.getDisplay());

		//create the main staging form
		this.mainForm = this.toolkit.createForm(parent);
		this.mainForm.setText(Messages.AbapGitStaging_no_repository_selected);
		//TODO: icon
		GridDataFactory.fillDefaults().grab(true, true).applyTo(this.mainForm);
		this.toolkit.decorateFormHeading(this.mainForm);
		GridLayoutFactory.swtDefaults().applyTo(this.mainForm.getBody());

		//sash form holding staging section and commit section
		SashForm mainSashForm = createSashForm(this.mainForm.getBody(), SWT.HORIZONTAL);
		//sash form holding unstaged section and staged section
		SashForm stagingSashForm = createSashForm(mainSashForm, SWT.VERTICAL);

		this.createUnstagedComposite(stagingSashForm);
		this.createCommitComposite(mainSashForm);
		this.createStagedComposite(stagingSashForm);

		//do the initial validation
		validateInputs();
		toggleButtonsState();
	}

	private SashForm createSashForm(Composite parent, int style) {
		SashForm sashForm = new SashForm(parent, style);
		this.toolkit.adapt(sashForm, true, true);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(sashForm);
		return sashForm;
	}

	private void createUnstagedComposite(Composite parent) {
		this.unstagedSection = this.toolkit.createSection(parent, ExpandableComposite.SHORT_TITLE_BAR);
		this.unstagedSection.setText(Messages.AbapGitStaging_unstaged_changes_section_header + " (0)"); //$NON-NLS-1$
		this.unstagedSection.clientVerticalSpacing = 0;

		//unstaged section toolbar
		this.createUnstagedSectionToolbar();

		Composite unstagedComposite = this.toolkit.createComposite(this.unstagedSection);
		this.toolkit.paintBordersFor(unstagedComposite);
		GridLayoutFactory.fillDefaults().applyTo(unstagedComposite);
		this.unstagedSection.setClient(unstagedComposite);

		this.unstagedTreeViewer = this.createTreeViewer(unstagedComposite);
		addDragAndDropSupport(this.stagedTreeViewer, true);
	}

	private void createUnstagedSectionToolbar() {
		Composite unstagedToolbarComposite = this.toolkit.createComposite(this.unstagedSection);
		unstagedToolbarComposite.setBackground(null);
		RowLayoutFactory.fillDefaults().applyTo(unstagedToolbarComposite);
		this.unstagedSection.setTextClient(unstagedToolbarComposite);

		this.unstagedToolbarManager = new ToolBarManager(SWT.FLAT | SWT.HORIZONTAL);
		this.unstagedToolbarManager.add(this.stageAction);
		this.unstagedToolbarManager.update(true);
		this.unstagedToolbarManager.createControl(unstagedToolbarComposite);

		//TODO : toolbar action for hide/show ignored files in unstaged changes tree viewer
	}

	private void createStagedComposite(Composite parent) {
		this.stagedSection = this.toolkit.createSection(parent, ExpandableComposite.SHORT_TITLE_BAR);
		this.stagedSection.setText(Messages.AbapGitStaging_staged_changes_section_header + " (0)"); //$NON-NLS-1$
		this.stagedSection.clientVerticalSpacing = 0;

		//staged section toolbar
		this.createStagedSectionToolbar();

		Composite stagedComposite = this.toolkit.createComposite(this.stagedSection);
		this.toolkit.paintBordersFor(stagedComposite);
		GridLayoutFactory.fillDefaults().applyTo(stagedComposite);
		this.stagedSection.setClient(stagedComposite);

		this.stagedTreeViewer = this.createTreeViewer(stagedComposite);
		addDragAndDropSupport(this.stagedTreeViewer, false);
	}

	private void createStagedSectionToolbar() {
		Composite stagedToolbarComposite = this.toolkit.createComposite(this.stagedSection);
		stagedToolbarComposite.setBackground(null);
		RowLayoutFactory.fillDefaults().applyTo(stagedToolbarComposite);
		this.stagedSection.setTextClient(stagedToolbarComposite);

		this.stagedToolbarManager = new ToolBarManager(SWT.FLAT | SWT.HORIZONTAL);
		this.stagedToolbarManager.add(this.unstageAction);
		this.stagedToolbarManager.update(true);
		this.stagedToolbarManager.createControl(stagedToolbarComposite);
	}

	private TreeViewer createTreeViewer(Composite parent) {
		TreeViewer viewer = this.createTree(parent);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(viewer.getControl());
		viewer.getTree().setData(FormToolkit.KEY_DRAW_BORDER, FormToolkit.TREE_BORDER);
		viewer.setLabelProvider(new AbapGitStagingLabelProvider());
		viewer.setContentProvider(new AbapGitStagingContentProvider());
		return viewer;
	}

	private TreeViewer createTree(Composite composite) {
		Tree tree = this.toolkit.createTree(composite, SWT.FULL_SELECTION | SWT.MULTI);
		TreeViewer treeViewer = new TreeViewer(tree);
		treeViewer.setUseHashlookup(true);
		return treeViewer;
	}

	private void createCommitComposite(Composite parent) {
		this.commitSection = this.toolkit.createSection(parent, ExpandableComposite.SHORT_TITLE_BAR);
		this.commitSection.clientVerticalSpacing = 0;
		this.commitSection.setText(Messages.AbapGitStaging_commit_section_header);
		this.commitSection.setLayoutData(GridDataFactory.fillDefaults().grab(true, true).create());

		Composite commitComposite = this.toolkit.createComposite(this.commitSection);
		GridLayoutFactory.fillDefaults().numColumns(1).applyTo(commitComposite);
		this.commitSection.setClient(commitComposite);

		//commit section : warnings and errors composite
		createToggleableWarningsComposite(commitComposite);
		//commit message text
		createCommitMessageComposite(commitComposite);

		Composite commitPersonComposite = this.toolkit.createComposite(commitComposite);
		this.toolkit.paintBordersFor(commitPersonComposite);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(commitPersonComposite);
		GridLayoutFactory.swtDefaults().margins(1, 2).numColumns(3).spacing(1, LayoutConstants.getSpacing().y)
				.applyTo(commitPersonComposite);

		//author
		createAuthorComposite(commitPersonComposite);
		//committer
		createCommitterComposite(commitPersonComposite);

		//commit button
		this.commitAndPushButton = this.toolkit.createButton(commitComposite, Messages.AbapGitStaging_commit_button, SWT.PUSH);
		this.commitAndPushButton.setLayoutData(GridDataFactory.fillDefaults().align(SWT.RIGHT, SWT.BEGINNING).create());
		//TODO: icon
		SWTUtil.setButtonWidthHint(this.commitAndPushButton);
		this.commitAndPushButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				commit();
			}
		});
	}

	private void createToggleableWarningsComposite(Composite parent) {
		this.warningsComposite = new Composite(parent, SWT.NONE);
		this.warningsComposite.setVisible(false);
		this.warningsComposite.setLayout(new GridLayout(2, false));
		this.warningsComposite.setLayoutData(new GridData());
		changeWarningsVisibility(false); //hide the composite initially

		Label image = new Label(this.warningsComposite, SWT.NONE);
		GridDataFactory.fillDefaults().align(SWT.BEGINNING, SWT.BEGINNING).applyTo(image);
		image.setImage(PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJS_WARN_TSK));
		this.warningText = new Label(this.warningsComposite, SWT.WRAP);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(this.warningText);
	}

	private void changeWarningsVisibility(boolean visible) {
		this.warningsComposite.setVisible(visible);
		GridData data = (GridData) this.warningsComposite.getLayoutData();
		data.exclude = !visible;
		this.warningsComposite.getParent().layout();
	}

	private void createCommitMessageComposite(Composite parent) {
		Composite commitMessageComposite = this.toolkit.createComposite(parent);
		this.toolkit.paintBordersFor(commitMessageComposite);
		GridLayoutFactory.fillDefaults().numColumns(1).applyTo(commitMessageComposite);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(commitMessageComposite);

		this.commitMessageTextViewer = new TextViewer(commitMessageComposite, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
		this.commitMessageTextViewer.setData(FormToolkit.KEY_DRAW_BORDER, FormToolkit.TREE_BORDER);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(this.commitMessageTextViewer.getTextWidget());
		this.commitMessageTextViewer.getTextWidget().setAlwaysShowScrollBars(false);
		this.commitMessageTextViewer.getTextWidget().setFont(JFaceResources.getTextFont());
		SWTUtil.addTextEditMenu(this.commitMessageTextViewer.getTextWidget());
		//draw a line to hint the max commit line length
		createMarginPainter(this.commitMessageTextViewer);

		this.commitMessageTextViewer.getTextWidget().addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				validateInputs();
			}
		});
	}

	private void createMarginPainter(TextViewer commitMessageTextViewer) {
		MarginPainter marginPainter = new MarginPainter(commitMessageTextViewer);
		marginPainter.setMarginRulerColumn(MAX_COMMIT_MESSAGE_LINE_LENGTH);
		marginPainter.setMarginRulerColor(PlatformUI.getWorkbench().getDisplay().getSystemColor(SWT.COLOR_GRAY));
		commitMessageTextViewer.addPainter(marginPainter);
	}

	private void createAuthorComposite(Composite parent) {
		//TODO: icon
		this.createPersonLabel(parent, null, Messages.AbapGitStaging_author);
		this.authorText = this.toolkit.createText(parent, null);
		this.authorText.setData(FormToolkit.KEY_DRAW_BORDER, FormToolkit.TREE_BORDER);
		this.authorText.setLayoutData(GridDataFactory.fillDefaults().indent(5, 0).grab(true, false).create());
		this.authorText.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				validateInputs();
			}
		});
	}

	private void createCommitterComposite(Composite parent) {
		//TODO: icon
		this.createPersonLabel(parent, null, Messages.AbapGitStaging_committer);
		this.committerText = this.toolkit.createText(parent, null);
		this.committerText.setData(FormToolkit.KEY_DRAW_BORDER, FormToolkit.TREE_BORDER);
		this.committerText.setLayoutData(GridDataFactory.fillDefaults().indent(5, 0).grab(true, false).create());
		this.committerText.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				validateInputs();
			}
		});
	}

	private void createPersonLabel(Composite parent, Image image, String text) {
		Label imageLabel = new Label(parent, 0);
		imageLabel.setImage(image);
		Label textLabel = this.toolkit.createLabel(parent, text);
		textLabel.setForeground(this.toolkit.getColors().getColor("org.eclipse.ui.forms.TB_TOGGLE")); //$NON-NLS-1$
	}

	private void contributeToActionBars() {
		IToolBarManager toolBarManager = getViewSite().getActionBars().getToolBarManager();

		//refresh action
		if (this.actionRefresh != null) {
			toolBarManager.add(this.actionRefresh);
		}
	}

	private void createActions() {
		//refresh repository action
		if (this.actionRefresh == null) {
			createRefreshAction();
		}

		//stage object action
		if (this.stageAction == null) {
			createStageAction();
		}

		//un-stage object action
		if (this.unstageAction == null) {
			createUnstageAction();
		}
	}

	/**
	 * Refresh AbapGit repository action
	 */
	private void createRefreshAction() {
		this.actionRefresh = new Action() {
			public void run() {
				//refresh the view
				loadStagingView(AbapGitStagingView.this.currentRepo, AbapGitStagingView.this.project);
			}
		};
		this.actionRefresh.setText(Messages.AbapGitView_action_refresh);
		this.actionRefresh.setToolTipText(Messages.AbapGitView_action_refresh);
		this.actionRefresh
				.setImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin(AbapGitUIPlugin.PLUGIN_ID, "icons/etool/refresh.png")); //$NON-NLS-1$
	}

	/**
	 * Action for staging an object from unstaged section to staged section
	 */
	private void createStageAction() {
		this.stageAction = new Action(Messages.AbapGitStaging_action_stage,
				PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_OBJ_ADD)) {
			public void run() {
				IStructuredSelection selection = (IStructuredSelection) AbapGitStagingView.this.unstagedTreeViewer.getSelection();
				stageSelectedObjects(selection);
			}
		};
	}

	/**
	 * Action for unstaging an object from staged section to unstaged section
	 */
	private void createUnstageAction() {
		//TODO: icon
		this.unstageAction = new Action(Messages.AbapGitStaging_action_unstage,
				PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_ELCL_REMOVE)) {
			public void run() {
				IStructuredSelection selection = (IStructuredSelection) AbapGitStagingView.this.stagedTreeViewer.getSelection();
				unstageSelectedObjects(selection);
			}
		};
	}

	private void stageSelectedObjects(IStructuredSelection selection) {
		List<IAbapGitObject> abapGitObjects = getAbapObjectsFromSelection(selection);
		this.stagingModel.getUnstagedObjects().getAbapgitobject().removeAll(abapGitObjects);
		this.stagingModel.getStagedObjects().getAbapgitobject().addAll(abapGitObjects);
		refresh();
	}

	private void unstageSelectedObjects(IStructuredSelection selection) {
		List<IAbapGitObject> abapGitObjects = getAbapObjectsFromSelection(selection);
		this.stagingModel.getStagedObjects().getAbapgitobject().removeAll(abapGitObjects);
		this.stagingModel.getUnstagedObjects().getAbapgitobject().addAll(abapGitObjects);
		refresh();
	}

	private List<IAbapGitObject> getAbapObjectsFromSelection(IStructuredSelection selection) {
		List<IAbapGitObject> abapGitObjects = new ArrayList<>();
		for (Object object : selection.toList()) {
			if (object instanceof IAbapGitObject) {
				abapGitObjects.add((IAbapGitObject) object);
			} else if (object instanceof IAbapGitFile) { //TODO : validate whether this behavior is okay for the first release, to stage/unstage the complete object even if a single file is selected under the object
				abapGitObjects.add((IAbapGitObject) ((IAbapGitFile) object).eContainer());
			}
		}
		return abapGitObjects;
	}

	/**
	 * Refreshes the staging view
	 */
	private void refresh() {
		//TODO: implement logic
	}

	/**
	 * Validator method for UI inputs
	 */
	private void validateInputs() {
		//TODO: implement logic
	}

	private void commit() {
		//TODO: implement
	}

	@Override
	public void loadStagingView(IRepository repository, IProject project) {
		//TODO: implement
	}

	@Override
	public void setFocus() {
		this.unstagedTreeViewer.getControl().setFocus();
	}

	private void toggleButtonsState() {
		this.commitAndPushButton.setEnabled(this.currentRepo == null ? false : true); //disabled if no repository is loaded
		this.stageAction.setEnabled(this.unstagedTreeViewer.getTree().getItemCount() > 0 ? true : false);
		this.unstageAction.setEnabled(this.stagedTreeViewer.getTree().getItemCount() > 0 ? true : false);
	}

	private void addDragAndDropSupport(TreeViewer viewer, final boolean unstaged) {
		viewer.addDragSupport(DND.DROP_MOVE | DND.DROP_COPY | DND.DROP_LINK,
				new Transfer[] { LocalSelectionTransfer.getTransfer(), LocalSelectionTransfer.getTransfer() },
				new StagingDragListener(viewer, ArrayContentProvider.getInstance(), unstaged));

		viewer.addDropSupport(DND.DROP_MOVE | DND.DROP_COPY | DND.DROP_LINK, new Transfer[] { LocalSelectionTransfer.getTransfer() },
				new DropTargetAdapter() {
					public void drop(DropTargetEvent event) {
						event.detail = DND.DROP_COPY;
						if (event.data instanceof IStructuredSelection) {
							IStructuredSelection selection = (IStructuredSelection) event.data;
							if (selection instanceof StagingDragSelection
									&& ((StagingDragSelection) selection).isFromUnstaged() == unstaged) {
								return;
							}
							if (((StagingDragSelection) selection).isFromUnstaged()) {
								stageSelectedObjects(selection);
							} else {
								unstageSelectedObjects(selection);
							}
						}
					}
				});
	}

}
