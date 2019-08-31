package org.abapgit.adt.ui.internal.views;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.abapgit.adt.backend.IExternalRepositoryInfo;
import org.abapgit.adt.backend.IExternalRepositoryInfo.AccessMode;
import org.abapgit.adt.backend.IExternalRepositoryInfoRequest;
import org.abapgit.adt.backend.IExternalRepositoryInfoService;
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
import org.abapgit.adt.ui.dialogs.AbapGitStagingCredentialsDialog;
import org.abapgit.adt.ui.internal.i18n.Messages;
import org.abapgit.adt.ui.internal.util.StagingDragListener;
import org.abapgit.adt.ui.internal.util.StagingDragSelection;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.bindings.keys.KeyStroke;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.layout.LayoutConstants;
import org.eclipse.jface.layout.RowLayoutFactory;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.MarginPainter;
import org.eclipse.jface.text.TextViewer;
import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTargetAdapter;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
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
import org.eclipse.ui.progress.IProgressService;

import com.sap.adt.communication.exceptions.CommunicationException;
import com.sap.adt.communication.resources.ResourceException;
import com.sap.adt.destinations.ui.logon.AdtLogonServiceUIFactory;
import com.sap.adt.tools.core.AdtObjectReference;
import com.sap.adt.tools.core.IAdtObjectReference;
import com.sap.adt.tools.core.model.util.AdtObjectReferenceAdapterFactory;
import com.sap.adt.tools.core.project.AdtProjectServiceFactory;
import com.sap.adt.tools.core.ui.navigation.AdtNavigationServiceFactory;
import com.sap.adt.util.ui.SWTUtil;

public class AbapGitStagingView extends ViewPart implements IAbapGitStagingView {

	public static final String VIEW_ID = "org.abapgit.adt.ui.views.AbapGitStagingView"; //$NON-NLS-1$

	private static final int MAX_COMMIT_MESSAGE_LINE_LENGTH = 72;
	private static final Pattern ANY_NON_WHITESPACE = Pattern.compile("[^\\h\\v]"); //$NON-NLS-1$

	private IProject project;
	private String destinationId;
	private IRepositoryService repoService;

	//UI Model
	private IAbapGitStaging model;
	private IRepository repository;
	private IExternalRepositoryInfo repositoryExternalInfo;
	private IExternalRepositoryInfoRequest repositoryCredentials;

	//UI controls
	private FormToolkit toolkit;
	private Form mainForm;

	//unstaged section controls
	private Section unstagedSection;
	private TreeViewer unstagedTreeViewer;
	private ToolBarManager unstagedToolbarManager;
	private AbapGitStagingObjectMenuFactory unstagedMenuFactory;

	//staged section controls
	private Section stagedSection;
	private TreeViewer stagedTreeViewer;
	private ToolBarManager stagedToolbarManager;
	private AbapGitStagingObjectMenuFactory stagedMenuFactory;

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

	private static final KeyStroke KEY_STROKE_COPY = KeyStroke.getInstance(SWT.ALT, 'C' | 'c');

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

		this.unstagedTreeViewer = this.createTreeViewer(unstagedComposite, true);
		addDragAndDropSupport(this.unstagedTreeViewer, true);

		this.unstagedMenuFactory = new AbapGitStagingObjectMenuFactory(this.unstagedTreeViewer, true, this);
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

		this.stagedTreeViewer = this.createTreeViewer(stagedComposite, false);
		addDragAndDropSupport(this.stagedTreeViewer, false);

		this.stagedMenuFactory = new AbapGitStagingObjectMenuFactory(this.stagedTreeViewer, false, this);
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

	private TreeViewer createTreeViewer(Composite parent, Boolean unstaged) {
		TreeViewer viewer = this.createTree(parent);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(viewer.getControl());
		viewer.getTree().setData(FormToolkit.KEY_DRAW_BORDER, FormToolkit.TREE_BORDER);
		viewer.setLabelProvider(new AbapGitStagingLabelProvider());
		viewer.setContentProvider(new AbapGitStagingContentProvider());
		addDoubleClickListener(viewer);
		addKeyListener(viewer, unstaged);
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
				commitAndPush();
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
				if (AbapGitStagingView.this.repository != null && AbapGitStagingView.this.project != null) {
					refresh();
				}
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

	protected void stageSelectedObjects(IStructuredSelection selection) {
		//TODO : implement single file mode
		List<IAbapGitObject> abapGitObjects = getAbapObjectsFromSelection(selection);
		this.model.getUnstagedObjects().getAbapgitobject().removeAll(abapGitObjects);
		this.model.getStagedObjects().getAbapgitobject().addAll(abapGitObjects);
		refreshUI();
	}

	protected void unstageSelectedObjects(IStructuredSelection selection) {
		//TODO : implement single file mode
		List<IAbapGitObject> abapGitObjects = getAbapObjectsFromSelection(selection);
		this.model.getStagedObjects().getAbapgitobject().removeAll(abapGitObjects);
		this.model.getUnstagedObjects().getAbapgitobject().addAll(abapGitObjects);
		refreshUI();
	}

	private List<IAbapGitObject> getAbapObjectsFromSelection(IStructuredSelection selection) {
		List<IAbapGitObject> abapGitObjects = new ArrayList<>();
		IAbapGitObject abapObject = null;
		for (Object object : selection.toList()) {
			if (object instanceof IAbapGitObject) {
				abapObject = (IAbapGitObject) object;
			} else if (object instanceof IAbapGitFile) {
				abapObject = (IAbapGitObject) ((IAbapGitFile) object).eContainer();
			}
			if (!abapGitObjects.contains(abapObject)) {
				abapGitObjects.add(abapObject);
			}
		}
		return abapGitObjects;
	}

	@Override
	public void openStagingView(IRepository repository, IProject project) {
		if (repository != null && project != null) {
			loadRepository(repository, project);
		}
	}

	private void loadRepository(IRepository repository, IProject project) {
		//reset the staging view to initial
		resetStagingView();

		//logon if required
		if (!AdtLogonServiceUIFactory.createLogonServiceUI().ensureLoggedOn(project).isOK()) {
			return;
		}

		AbapGitStagingView.this.project = project;
		AbapGitStagingView.this.destinationId = getDestination(project);
		this.repoService = getRepositoryService(this.destinationId);
		if (this.repoService == null) { //AbapGit not supported
			MessageDialog.openError(getSite().getShell(),
					NLS.bind(Messages.AbapGitView_not_supported, AbapGitStagingView.this.project.getName()),
					NLS.bind(Messages.AbapGitView_not_supported, AbapGitStagingView.this.project.getName()));
			return;
		}

		AbapGitStagingView.this.repository = repository;
		//load staging data and refresh UI
		refresh();
	}

	private boolean checkIfCredentialsRequired(IRepository repository, IProgressMonitor monitor) {
		if (this.repositoryExternalInfo == null) {
			monitor.beginTask(Messages.AbapGitWizardPageRepositoryAndCredentials_task_fetch_repo_info, IProgressMonitor.UNKNOWN);
			AbapGitStagingView.this.repositoryExternalInfo = getExternalRepositoryInfo(repository, monitor);
		}

		//check whether the repo is private
		if (AbapGitStagingView.this.repositoryExternalInfo.getAccessMode() == AccessMode.PRIVATE) {
			return true;
		}else {
			return false;
		}
	}

	private boolean getUserCredentials() {
		//open the user credentials pop-up
		Display.getDefault().syncExec(new Runnable() {
			@Override
			public void run() {
				Dialog userCredentialsDialog = new AbapGitStagingCredentialsDialog(getSite().getShell());
				if (userCredentialsDialog.open() == IDialogConstants.OK_ID) {
					AbapGitStagingView.this.repositoryCredentials = ((AbapGitStagingCredentialsDialog) userCredentialsDialog)
							.getExternalRepoInfo();
				}
			}
		});

		return this.repositoryCredentials != null ? true : false;
	}

	private void refresh() {
		Job refreshJob = new Job(Messages.AbapGitView_task_fetch_repos_staging) {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				try {
					if (AbapGitStagingView.this.repositoryCredentials == null) {
						if (checkIfCredentialsRequired(AbapGitStagingView.this.repository, monitor)) {
							if (!getUserCredentials()) {
								return Status.CANCEL_STATUS;
							}
						}
					}
					//fetch the staging data
					monitor.beginTask(Messages.AbapGitView_task_fetch_repos_staging, IProgressMonitor.UNKNOWN);
					AbapGitStagingView.this.model = AbapGitStagingView.this.repoService
							.stage(AbapGitStagingView.this.repository, AbapGitStagingView.this.repositoryCredentials, monitor);

					if (AbapGitStagingView.this.model != null) {
						//refresh UI
						refreshUI();
					}
				} catch (CommunicationException | ResourceException | OperationCanceledException e) {
					openErrorDialog(Messages.AbapGitView_task_fetch_repos_staging_error, e.getMessage(), true);
				}
				return Status.OK_STATUS;
			}
		};

		IProgressService service = PlatformUI.getWorkbench().getProgressService();
		service.showInDialog(getSite().getShell(), refreshJob);
		refreshJob.schedule();
	}

	/**
	 * Returns the repository details
	 */
	private IExternalRepositoryInfo getExternalRepositoryInfo(IRepository repository, IProgressMonitor monitor) {
		IExternalRepositoryInfoService externalRepoInfoService = RepositoryServiceFactory
				.createExternalRepositoryInfoService(AbapGitStagingView.this.destinationId, monitor);
		return externalRepoInfoService.getExternalRepositoryInfo(repository.getUrl(), null, null, monitor);
	}

	/**
	 * Refreshes the staging view
	 */
	private void refreshUI() {
		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				//TODO : have a better format for form header
				AbapGitStagingView.this.mainForm.setText(getRepoNameFromUrl(AbapGitStagingView.this.repository.getUrl()) + " [" //$NON-NLS-1$
						+ AbapGitStagingView.this.repository.getPackage() + "]" + " [" //$NON-NLS-1$//$NON-NLS-2$
						+ getBranchName(AbapGitStagingView.this.repository.getBranch()) + "]" + " [" //$NON-NLS-1$//$NON-NLS-2$
						+ AbapGitStagingView.this.project.getName() + "]"); //$NON-NLS-1$

				List<IAbapGitObject> unstagedInput = getUnstagedObjectsFromModel(AbapGitStagingView.this.model);
				//TODO : enable ignored objects handling
				List<IAbapGitObject> stagedInput = getStagedObjectsFromModel(AbapGitStagingView.this.model);

				//update the tree viewers
				AbapGitStagingView.this.unstagedTreeViewer.setInput(unstagedInput);
				AbapGitStagingView.this.stagedTreeViewer.setInput(stagedInput);

				//update the commit message section
				AbapGitStagingView.this.authorText
						.setText(AbapGitStagingView.this.model.getCommitMessage().getAuthor().getName() + " <" //$NON-NLS-1$
								+ AbapGitStagingView.this.model.getCommitMessage().getAuthor().getEmail() + ">"); //$NON-NLS-1$
				AbapGitStagingView.this.committerText
						.setText(AbapGitStagingView.this.model.getCommitMessage().getCommitter().getName() + " <" //$NON-NLS-1$
								+ AbapGitStagingView.this.model.getCommitMessage().getCommitter().getEmail() + ">"); //$NON-NLS-1$

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
		resetModel();
		resetUI();
	}

	private void resetModel() {
		this.model = null;
		this.repository = null;
		this.repositoryExternalInfo = null;
		this.repositoryCredentials = null;
		this.project = null;
		this.destinationId = null;
		this.repoService = null;
	}

	private void resetUI() {
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
		this.commitAndPushButton.setEnabled(this.repository == null ? false : true); //disabled if no repository is loaded
		this.stageAction.setEnabled(this.unstagedTreeViewer.getTree().getItemCount() > 0 ? true : false);
		this.unstageAction.setEnabled(this.stagedTreeViewer.getTree().getItemCount() > 0 ? true : false);
		this.actionRefresh.setEnabled(this.repository == null ? false : true);
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

	private void commitAndPush() {
		//check if any repo is selected
		if (this.repository == null) {
			MessageDialog.openWarning(getSite().getShell(), Messages.AbapGitStaging_no_repository_selected,
					Messages.AbapGitStaging_commit_error_no_repo_loaded_desc);
			return;
		}

		//check commit message
		String commitMessage = this.commitMessageTextViewer.getTextWidget().getText();
		if (!ANY_NON_WHITESPACE.matcher(commitMessage).find()) {
			MessageDialog.openWarning(getSite().getShell(), Messages.AbapGitStaging_commit_error_invalid_commit_msg_title,
					Messages.AbapGitStaging_commit_error_invalid_commit_msg_desc);
			return;
		}

		//check author
		IAuthor author;
		if ((author = getAuthorFromUIText(this.authorText.getText())) == null) {
			MessageDialog.openWarning(getSite().getShell(), Messages.AbapGitStaging_commit_error_invalid_author_title,
					Messages.AbapGitStaging_invalid_author);
			return;
		}

		//check committer
		ICommitter committer;
		if ((committer = getCommitterFromUIText(this.committerText.getText())) == null) {
			MessageDialog.openWarning(getSite().getShell(), Messages.AbapGitStaging_commit_error_invalid_committer_title,
					Messages.AbapGitStaging_invalid_committer);
			return;
		}

		//update model
		this.model.getCommitMessage().setMessage(commitMessage);
		this.model.getCommitMessage().setAuthor(author);
		this.model.getCommitMessage().setCommitter(committer);

		//get credentials
		if (this.repositoryCredentials == null) {
			//open the user credentials pop-up
			Dialog userCredentialsDialog = new AbapGitStagingCredentialsDialog(getSite().getShell());
			if (userCredentialsDialog.open() == IDialogConstants.OK_ID) {
				AbapGitStagingView.this.repositoryCredentials = ((AbapGitStagingCredentialsDialog) userCredentialsDialog)
						.getExternalRepoInfo();
			} else {
				return;
			}
		}

		//push
		Job pushJob = new Job(Messages.AbapGitStaging_push_job_title) {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				try {
					AbapGitStagingView.this.repoService.push(new NullProgressMonitor(), AbapGitStagingView.this.model,
							AbapGitStagingView.this.repository, AbapGitStagingView.this.repositoryCredentials);

					//push successful
					Display.getDefault().syncExec(new Runnable() {
						public void run() {
							MessageDialog.openInformation(getSite().getShell(), Messages.AbapGitStaging_push_job_successful,
									Messages.AbapGitStaging_push_job_successful);
							AbapGitStagingView.this.commitMessageTextViewer.getTextWidget().setText(""); //$NON-NLS-1$
							refresh(); // refresh
						}
					});
					return Status.OK_STATUS;
				} catch (CommunicationException | ResourceException | OperationCanceledException e) {
					openErrorDialog(Messages.AbapGitStaging_push_job_error, e.getMessage(), true);
				}
				return Status.CANCEL_STATUS;
			}
		};

		IProgressService service = PlatformUI.getWorkbench().getProgressService();
		service.showInDialog(getSite().getShell(), pushJob);
		pushJob.schedule();
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

	private void addDoubleClickListener(TreeViewer viewer) {
		viewer.addDoubleClickListener(new IDoubleClickListener() {
			@Override
			public void doubleClick(DoubleClickEvent event) {
				//open the object
				if (event.getSelection() instanceof IStructuredSelection) {
					IStructuredSelection selection = (IStructuredSelection) event.getSelection();
					Object object = selection.getFirstElement();
					if (object instanceof IAbapGitObject) {
						openAbapObject((IAbapGitObject) object);
					}
				}
			}
		});
	}

	private void addKeyListener(TreeViewer viewer, boolean unstaged) {
		viewer.getTree().addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.keyCode == KEY_STROKE_COPY.getNaturalKey() && e.stateMask == KEY_STROKE_COPY.getModifierKeys()) {
					IStructuredSelection selection = null;
					if (unstaged) {
						selection = (IStructuredSelection) AbapGitStagingView.this.unstagedTreeViewer.getSelection();
					} else {
						selection = (IStructuredSelection) AbapGitStagingView.this.stagedTreeViewer.getSelection();
					}
					if (selection != null && selection.getFirstElement() != null) {
						copy(selection.getFirstElement());
					}
				}
			}
		});
	}

	protected void openAbapObject(IAbapGitObject abapObject) {
		try {
			if (abapObject.getUri() != null && !abapObject.getUri().isEmpty()) {
				URI objectURI = new URI(abapObject.getUri());
				IAdtObjectReference ref = new AdtObjectReference(objectURI, abapObject.getName(), abapObject.getWbkey(), null);
				AdtNavigationServiceFactory.createNavigationService().navigate(AbapGitStagingView.this.project,
						AdtObjectReferenceAdapterFactory.createFromNonEmfReference(ref), true);
			}
		} catch (URISyntaxException e) {
			AbapGitUIPlugin.getDefault().getLog().log(new Status(IStatus.ERROR, AbapGitUIPlugin.PLUGIN_ID, e.getMessage(), e));
		}
	}

	protected void copy(Object object) {
		final StringBuilder data = new StringBuilder();

		if (object instanceof IAbapGitObject) {
			data.append(((IAbapGitObject) object).getName());
		} else if (object instanceof IAbapGitFile) {
			data.append(((IAbapGitFile) object).getName());
		}

		final Clipboard clipboard = new Clipboard(this.getViewSite().getShell().getDisplay());
		clipboard.setContents(new String[] { data.toString() }, new TextTransfer[] { TextTransfer.getInstance() });
		clipboard.dispose();
	}

	private static String getDestination(IProject project) {
		return AdtProjectServiceFactory.createProjectService().getDestinationId(project);
	}

	private IRepositoryService getRepositoryService(String destinationId) {
		return RepositoryServiceFactory.createRepositoryService(destinationId, new NullProgressMonitor());
	}

	private void openErrorDialog(String title, String message, boolean runInUIThread) {
		if (runInUIThread) {
			Display.getDefault().syncExec(new Runnable() {
				public void run() {
					MessageDialog.openError(getSite().getShell(), title, message);
				}
			});
		} else {
			MessageDialog.openError(getSite().getShell(), title, message);
		}
	}

	@Override
	public void dispose() {
		super.dispose();
		this.unstagedMenuFactory.dispose();
		this.stagedMenuFactory.dispose();
	}

}
