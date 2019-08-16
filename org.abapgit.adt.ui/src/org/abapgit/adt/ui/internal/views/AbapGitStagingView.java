package org.abapgit.adt.ui.internal.views;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.abapgit.adt.backend.IRepository;
import org.abapgit.adt.backend.IRepositoryService;
import org.abapgit.adt.backend.RepositoryServiceFactory;
import org.abapgit.adt.backend.model.abapgitstaging.IAbapGitFile;
import org.abapgit.adt.backend.model.abapgitstaging.IAbapGitObject;
import org.abapgit.adt.backend.model.abapgitstaging.IAbapGitStaging;
import org.abapgit.adt.backend.model.abapgitstaging.IAbapgitstagingFactory;
import org.abapgit.adt.backend.model.abapgitstaging.IAuthor;
import org.abapgit.adt.backend.model.abapgitstaging.ICommitter;
import org.abapgit.adt.ui.AbapGitUIPlugin;
import org.abapgit.adt.ui.internal.i18n.Messages;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.layout.LayoutConstants;
import org.eclipse.jface.layout.RowLayoutFactory;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.MarginPainter;
import org.eclipse.jface.text.TextViewer;
import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.osgi.util.NLS;
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
import org.eclipse.swt.widgets.Display;
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
import org.eclipse.ui.statushandlers.StatusManager;

import com.sap.adt.tools.core.project.AdtProjectServiceFactory;
import com.sap.adt.util.ui.SWTUtil;

public class AbapGitStagingView extends ViewPart implements IAbapGitStagingView {

	public static final String VIEW_ID = "org.abapgit.adt.ui.views.AbapGitStagingView"; //$NON-NLS-1$

	private static final int MAX_COMMIT_MESSAGE_LINE_LENGTH = 72;

	private IProject project;
	private String destinationId;
	private IRepository currentRepo;
	private IAbapGitStaging stagingModel;
	private IRepositoryService repoService;

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

		validateInputs(); //do the initial validation
		updateButtonsState(); //update button state
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
		addDragAndDropSupport(this.unstagedTreeViewer, true);
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

	@Override
	public void loadStagingView(IRepository repository, IProject project) {
		if (repository != null && project != null) {
			resetStagingView(); //reset view

			AbapGitStagingView.this.project = project;
			AbapGitStagingView.this.destinationId = getDestination(project);
			if (this.destinationId != null) {
				this.repoService = getRepositoryService(this.destinationId);
				if (this.repoService == null) { //AbapGit not supported
					MessageDialog.openError(getSite().getShell(),
							NLS.bind(Messages.AbapGitView_not_supported, AbapGitStagingView.this.project.getName()),
							NLS.bind(Messages.AbapGitView_not_supported, AbapGitStagingView.this.project.getName()));

					return;
				}

				try {
					PlatformUI.getWorkbench().getProgressService().busyCursorWhile(new IRunnableWithProgress() {
						@Override
						public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
							monitor.beginTask(Messages.AbapGitView_task_fetch_repos_staging, IProgressMonitor.UNKNOWN);
							AbapGitStagingView.this.stagingModel = AbapGitStagingView.this.repoService.getRepositoryStaging(repository,
									monitor);
							if (AbapGitStagingView.this.stagingModel != null) {
								AbapGitStagingView.this.currentRepo = repository;
								refresh(); //refresh view
							}
						}
					});

				} catch (InvocationTargetException | InterruptedException e) {
					StatusManager.getManager().handle(new Status(IStatus.ERROR, AbapGitUIPlugin.PLUGIN_ID,
							Messages.AbapGitView_task_fetch_repos_staging_error, e.getCause()), StatusManager.SHOW);
				}
			}
		}
	}

	/**
	 * Refreshes the staging view
	 */
	private void refresh() {
		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				//TODO : have a better format for form header
				AbapGitStagingView.this.mainForm.setText(getRepoNameFromUrl(AbapGitStagingView.this.currentRepo.getUrl()) + " [" //$NON-NLS-1$
						+ AbapGitStagingView.this.currentRepo.getPackage() + "]" + " [" //$NON-NLS-1$//$NON-NLS-2$
						+ getBranchName(AbapGitStagingView.this.currentRepo.getBranch()) + "]" + " [" //$NON-NLS-1$//$NON-NLS-2$
						+ AbapGitStagingView.this.project.getName() + "]"); //$NON-NLS-1$

				List<IAbapGitObject> unstagedInput = getUnstagedObjectsFromModel(AbapGitStagingView.this.stagingModel);
				//TODO : enable ignored objects handling
				List<IAbapGitObject> stagedInput = getStagedObjectsFromModel(AbapGitStagingView.this.stagingModel);

				//update the tree viewers
				AbapGitStagingView.this.unstagedTreeViewer.setInput(unstagedInput);
				AbapGitStagingView.this.stagedTreeViewer.setInput(stagedInput);

				//update the commit message section
				AbapGitStagingView.this.authorText
						.setText(AbapGitStagingView.this.stagingModel.getCommitMessage().getAuthor().getName() + " <" //$NON-NLS-1$
								+ AbapGitStagingView.this.stagingModel.getCommitMessage().getAuthor().getEmail() + ">"); //$NON-NLS-1$
				AbapGitStagingView.this.committerText
						.setText(AbapGitStagingView.this.stagingModel.getCommitMessage().getCommitter().getName() + " <" //$NON-NLS-1$
								+ AbapGitStagingView.this.stagingModel.getCommitMessage().getCommitter().getEmail() + ">"); //$NON-NLS-1$

				updateSectionHeaders();
				updateButtonsState();
				validateInputs();
			}
		});
	}

	/**
	 * Resets the staging view
	 */
	private void resetStagingView() {
		this.stagingModel = null;
		this.currentRepo = null;

		this.mainForm.setText(Messages.AbapGitStaging_no_repository_selected);
		this.commitMessageTextViewer.getTextWidget().setText(""); //$NON-NLS-1$
		this.authorText.setText(""); //$NON-NLS-1$
		this.committerText.setText(""); //$NON-NLS-1$

		this.unstagedTreeViewer.setInput(null);
		this.stagedTreeViewer.setInput(null);

		updateSectionHeaders();
		updateButtonsState();
	}

	/**
	 * Updates the header text for unstaged and staged changes section with the
	 * tree item count
	 */
	private void updateSectionHeaders() {
		//update the unstaged section header
		this.unstagedSection.setText(
				Messages.AbapGitStaging_unstaged_changes_section_header + " (" + this.unstagedTreeViewer.getTree().getItemCount() + ")"); //$NON-NLS-1$ //$NON-NLS-2$
		//update the staged section header
		this.stagedSection.setText(
				Messages.AbapGitStaging_staged_changes_section_header + " (" + this.stagedTreeViewer.getTree().getItemCount() + ")"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	/**
	 * Updates the state of UI buttons
	 */
	private void updateButtonsState() {
		this.commitAndPushButton.setEnabled(this.currentRepo == null ? false : true); //disabled if no repository is loaded
		this.stageAction.setEnabled(this.unstagedTreeViewer.getTree().getItemCount() > 0 ? true : false);
		this.unstageAction.setEnabled(this.stagedTreeViewer.getTree().getItemCount() > 0 ? true : false);
	}

	private List<IAbapGitObject> getUnstagedObjectsFromModel(IAbapGitStaging staging) {
		List<IAbapGitObject> objects = (List<IAbapGitObject>) staging.getUnstagedObjects().getAbapgitobject();
		return objects;
	}

	private List<IAbapGitObject> getStagedObjectsFromModel(IAbapGitStaging staging) {
		List<IAbapGitObject> objects = (List<IAbapGitObject>) staging.getStagedObjects().getAbapgitobject();
		return objects;
	}

	private String getRepoNameFromUrl(String repoURL) {
		if (repoURL != null && !repoURL.isEmpty()) {
			String[] tokens = repoURL.split("/"); //$NON-NLS-1$
			String repoName = tokens[tokens.length - 1];
			if (repoName.endsWith(".git")) { //$NON-NLS-1$
				repoName = repoName.replace(".git", ""); //$NON-NLS-1$ //$NON-NLS-2$
			}
			return repoName;
		}
		return null;
	}

	private String getBranchName(String branch) {
		if (branch != null && !branch.isEmpty()) {
			String[] tokens = branch.split("/"); //$NON-NLS-1$
			return tokens[tokens.length - 1];
		}
		return null;
	}

	/**
	 * Validator method for UI inputs
	 */
	private void validateInputs() {
		boolean valid = true;

		//check commit message
		String commitMessage = this.commitMessageTextViewer.getTextWidget().getText();
		IDocument commitMessageDocument = new Document(commitMessage);
		if (commitMessageDocument.getNumberOfLines() > 1) {
			try {
				IRegion lineInfo = commitMessageDocument.getLineInformation(1);
				if (lineInfo.getLength() > 0) {
					valid = false;
					setWarnings(Messages.AbapGitStaging_commit_message_second_line_not_empty);
				}
			} catch (BadLocationException e) {
				AbapGitUIPlugin.getDefault().getLog().log(new Status(IStatus.ERROR, AbapGitUIPlugin.PLUGIN_ID, e.getMessage(), e));
			}
		}

		//check author
		String authorText = this.authorText.getText();
		if (getAuthorFromUIText(authorText) == null) {
			setWarnings(Messages.AbapGitStaging_invalid_author);
			valid = false;
		}

		//check committer
		String committerText = this.committerText.getText();
		if (getCommitterFromUIText(committerText) == null) {
			setWarnings(Messages.AbapGitStaging_invalid_committer);
			valid = false;
		}

		if (valid) {
			hideWarnings();
		}
	}

	private IAuthor getAuthorFromUIText(String authorText) {
		if (!validatePersonInput(authorText)) {
			return null;
		}

		int indexEmailStartBracket = authorText.indexOf('<');
		int indexEmailEndBracket = authorText.indexOf('>');

		String name = authorText.substring(0, indexEmailStartBracket).trim();
		if (name == null || name.isEmpty()) {
			return null;
		}

		String email = authorText.substring(indexEmailStartBracket + 1, indexEmailEndBracket).trim();
		if (email == null || email.isEmpty()) {
			return null;
		}

		IAuthor author = IAbapgitstagingFactory.eINSTANCE.createAuthor();
		author.setEmail(email);
		author.setName(name);
		return author;
	}

	private ICommitter getCommitterFromUIText(String committerText) {
		if (!validatePersonInput(committerText)) {
			return null;
		}

		int indexEmailStartBracket = committerText.indexOf('<');
		int indexEmailEndBracket = committerText.indexOf('>');

		String name = committerText.substring(0, indexEmailStartBracket).trim();
		if (name == null || name.isEmpty()) {
			return null;
		}

		String email = committerText.substring(indexEmailStartBracket + 1, indexEmailEndBracket).trim();
		if (email == null || email.isEmpty()) {
			return null;
		}

		ICommitter committer = IAbapgitstagingFactory.eINSTANCE.createCommitter();
		committer.setEmail(email);
		committer.setName(name);
		return committer;
	}

	private boolean validatePersonInput(String text) {
		text = text.trim();
		int indexEmailStartBracket = text.indexOf('<');
		int indexEmailEndBracket = text.indexOf('>');

		//email start bracket not found
		if (indexEmailStartBracket == -1 || indexEmailStartBracket == 0) {
			return false;
		}
		//character before the email start bracket should be a space
		if (text.charAt(indexEmailStartBracket - 1) != ' ') {
			return false;
		}
		//email end bracket not found
		if (indexEmailEndBracket == -1) {
			return false;
		}
		//email end bracket before email start bracket
		if (indexEmailEndBracket < indexEmailStartBracket) {
			return false;
		}
		//email end bracket not end of text
		if (indexEmailEndBracket != text.length() - 1) {
			return false;
		}
		return true;
	}

	private void setWarnings(String message) {
		this.warningText.setText(message);
		changeWarningsVisibility(true);
	}

	private void hideWarnings() {
		changeWarningsVisibility(false);
	}

	private void commit() {
		//TODO: implement
	}

	@Override
	public void setFocus() {
		this.unstagedTreeViewer.getControl().setFocus();
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

	private static String getDestination(IProject project) {
		return AdtProjectServiceFactory.createProjectService().getDestinationId(project);
	}

	private IRepositoryService getRepositoryService(String destinationId) {
		return RepositoryServiceFactory.createRepositoryService(destinationId, new NullProgressMonitor());
	}

}
