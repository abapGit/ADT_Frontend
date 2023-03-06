package org.abapgit.adt.ui.internal.staging;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import org.abapgit.adt.backend.IExternalRepositoryInfoService;
import org.abapgit.adt.backend.IRepositoryService;
import org.abapgit.adt.backend.RepositoryServiceFactory;
import org.abapgit.adt.backend.model.abapgitexternalrepo.IExternalRepositoryInfo;
import org.abapgit.adt.backend.model.abapgitexternalrepo.IExternalRepositoryInfoRequest;
import org.abapgit.adt.backend.model.abapgitexternalrepo.impl.AbapgitexternalrepoFactoryImpl;
import org.abapgit.adt.backend.model.abapgitrepositories.IRepository;
import org.abapgit.adt.backend.model.abapgitstaging.IAbapGitCommitMessage;
import org.abapgit.adt.backend.model.abapgitstaging.IAbapGitObject;
import org.abapgit.adt.backend.model.abapgitstaging.IAbapGitStaging;
import org.abapgit.adt.backend.model.abapgitstaging.IAbapgitstagingFactory;
import org.abapgit.adt.backend.model.abapgitstaging.IAuthor;
import org.abapgit.adt.backend.model.abapgitstaging.ICommitter;
import org.abapgit.adt.backend.model.abapgitstagingobjectgrouping.IAbapGitStagingGroupNode;
import org.abapgit.adt.ui.AbapGitUIPlugin;
import org.abapgit.adt.ui.internal.i18n.Messages;
import org.abapgit.adt.ui.internal.repositories.actions.OpenRepositoryAction;
import org.abapgit.adt.ui.internal.staging.actions.CopyNameAction;
import org.abapgit.adt.ui.internal.staging.actions.OpenPackageAction;
import org.abapgit.adt.ui.internal.staging.util.AbapGitStagingGroupingUtil;
import org.abapgit.adt.ui.internal.staging.util.AbapGitStagingTreeComparator;
import org.abapgit.adt.ui.internal.staging.util.AbapGitStagingTreeFilter;
import org.abapgit.adt.ui.internal.staging.util.FileStagingModeUtil;
import org.abapgit.adt.ui.internal.staging.util.FileStagingModeUtilForGroupNode;
import org.abapgit.adt.ui.internal.staging.util.IAbapGitStagingService;
import org.abapgit.adt.ui.internal.staging.util.ObjectStagingModeUtil;
import org.abapgit.adt.ui.internal.staging.util.ProjectChangeListener;
import org.abapgit.adt.ui.internal.staging.util.StagingDragListener;
import org.abapgit.adt.ui.internal.staging.util.StagingDragSelection;
import org.abapgit.adt.ui.internal.staging.util.SwitchRepositoryMenuCreator;
import org.abapgit.adt.ui.internal.util.AbapGitUIServiceFactory;
import org.abapgit.adt.ui.internal.util.ErrorHandlingService;
import org.abapgit.adt.ui.internal.util.GitCredentialsService;
import org.abapgit.adt.ui.internal.util.RepositoryUtil;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.equinox.security.storage.ISecurePreferences;
import org.eclipse.equinox.security.storage.SecurePreferencesFactory;
import org.eclipse.equinox.security.storage.StorageException;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ControlContribution;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.bindings.keys.KeyStroke;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.layout.LayoutConstants;
import org.eclipse.jface.layout.RowLayoutFactory;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.resource.ImageDescriptor;
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
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.IFormColors;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.Form;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.ui.progress.IProgressService;
import org.eclipse.ui.statushandlers.StatusManager;

import com.sap.adt.communication.exceptions.CommunicationException;
import com.sap.adt.communication.resources.ResourceException;
import com.sap.adt.compatibility.exceptions.OutDatedClientException;
import com.sap.adt.util.ui.AdtUtilUiPlugin;
import com.sap.adt.util.ui.SWTUtil;

public class AbapGitStagingView extends ViewPart implements IAbapGitStagingView {

	public static final String VIEW_ID = "org.abapgit.adt.ui.views.AbapGitStagingView"; //$NON-NLS-1$

	private static final int MAX_COMMIT_MESSAGE_LINE_LENGTH = 72;
	private static final Pattern ANY_NON_WHITESPACE = Pattern.compile("[^\\h\\v]"); //$NON-NLS-1$

	protected IProject project;
	private String destinationId;
	protected IRepositoryService repoService;
	protected IExternalRepositoryInfoService repoExternaService;
	protected IAbapGitStagingService stagingUtil;
	private ProjectChangeListener projectChangeListener; // project change listener

	//UI Model
	private IAbapGitStaging model;
	protected IRepository repository;
	private IExternalRepositoryInfo repositoryExternalInfo;
	private IExternalRepositoryInfoRequest repositoryCredentials;

	//UI Model for grouping
	private IAbapGitStagingGrouping groupingModel;

	private FormToolkit toolkit;
	protected Form mainForm;
	protected Text filterText;
	private AbapGitStagingTreeFilter treeFilter;

	//unstaged section controls
	private Section unstagedSection;
	protected TreeViewer unstagedTreeViewer;
	private ToolBarManager unstagedToolbarManager;
	private AbapGitStagingObjectMenuFactory unstagedMenuFactory;
	private final List<EObject> unstagedTreeViewerInput = new ArrayList<>();

	//staged section controls
	private Section stagedSection;
	protected TreeViewer stagedTreeViewer;
	private ToolBarManager stagedToolbarManager;
	private AbapGitStagingObjectMenuFactory stagedMenuFactory;
	private final List<EObject> stagedTreeViewerInput = new ArrayList<>();

	//commit section controls
	private Section commitSection;
	private Composite warningsComposite;
	private Label warningText;
	protected TextViewer commitMessageTextViewer;
	protected Text authorText;
	protected Text committerText;
	protected Button commitAndPushButton;

	//actions
	protected IAction actionRefresh;
	protected IAction actionStage;
	protected IAction actionUnstage;
	private IAction actionOpenPackage;
	private IAction actionOpenRepository;
	private IAction actionCollapseAllUnstaged;
	private IAction actionCollapseAllStaged;
	private IAction actionSwitchRepository;

	protected IAction actionGroupByNone;
	protected IAction actionGroupByPackage;
	protected IAction actionGroupByTransport;
	protected String groupingSelection = Messages.AbapGitStagingView_GroupByNoneAction;

	//key binding for copy text
	private static final KeyStroke KEY_STROKE_COPY = KeyStroke.getInstance(SWT.MOD1, 'C' | 'c');

	/**
	 * Set singleFileStageMode = true, to enable file staging/unstaging and drag
	 * and drop mode. If it is set to false, always the parent object is moved
	 * across unstaged and staged changes even on drag and drop of a file under
	 * the parent object
	 */
	private static final boolean singleFileStageMode = true;
	private static final String stage = "stage"; //$NON-NLS-1$
	private static final String push = "push"; //$NON-NLS-1$
	private boolean credsRetrievedFromSecureStorage = false;

	public AbapGitStagingView() {
		this.stagingUtil = getStagingUtil();
	}

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
		this.mainForm
				.setImage(AbstractUIPlugin.imageDescriptorFromPlugin(AbapGitUIPlugin.PLUGIN_ID, "icons/obj/repository.png").createImage()); //$NON-NLS-1$

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

		//create project change listener
		this.projectChangeListener = new ProjectChangeListener(this, ResourcesPlugin.getWorkspace());
	}

	private SashForm createSashForm(Composite parent, int style) {
		SashForm sashForm = new SashForm(parent, style);
		this.toolkit.adapt(sashForm, true, true);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(sashForm);
		return sashForm;
	}

	private void createUnstagedComposite(Composite parent) {
		this.unstagedSection = this.toolkit.createSection(parent, ExpandableComposite.SHORT_TITLE_BAR | Section.DESCRIPTION);
		this.unstagedSection.setText(Messages.AbapGitStaging_unstaged_changes_section_header + " (0)"); //$NON-NLS-1$
		this.unstagedSection.setDescription(Messages.AbapGitStagingView_unstagedSectionDescription);
		this.unstagedSection.setToolTipText(
				Messages.AbapGitStagingView_unstagedSectionToolTip);

		this.unstagedSection.clientVerticalSpacing = 0;

		//unstaged section toolbar
		this.createUnstagedSectionToolbar();

		Composite unstagedComposite = this.toolkit.createComposite(this.unstagedSection);
		this.toolkit.paintBordersFor(unstagedComposite);
		GridLayoutFactory.fillDefaults().applyTo(unstagedComposite);
		this.unstagedSection.setClient(unstagedComposite);

		//create the treeviewer
		this.unstagedTreeViewer = this.createTreeViewer(unstagedComposite, true);
		this.unstagedTreeViewer.setInput(this.unstagedTreeViewerInput);
		addDragAndDropSupport(this.unstagedTreeViewer, true);

		//add context menu support to the tree viewer
		this.unstagedMenuFactory = new AbapGitStagingObjectMenuFactory(this.unstagedTreeViewer, true, this, this.stagingUtil);
	}

	private void createUnstagedSectionToolbar() {
		Composite unstagedToolbarComposite = this.toolkit.createComposite(this.unstagedSection);
		unstagedToolbarComposite.setBackground(null);
		RowLayoutFactory.fillDefaults().applyTo(unstagedToolbarComposite);
		this.unstagedSection.setTextClient(unstagedToolbarComposite);

		this.unstagedToolbarManager = new ToolBarManager(SWT.FLAT | SWT.HORIZONTAL);
		this.unstagedToolbarManager.add(this.actionStage);
		this.unstagedToolbarManager.add(new Separator());
		this.actionCollapseAllUnstaged = new CollapseAllAction(true);
		this.unstagedToolbarManager.add(this.actionCollapseAllUnstaged);
		this.unstagedToolbarManager.update(true);
		this.unstagedToolbarManager.createControl(unstagedToolbarComposite);
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

		//create the treeviewer
		this.stagedTreeViewer = this.createTreeViewer(stagedComposite, false);
		this.stagedTreeViewer.setInput(this.stagedTreeViewerInput);
		addDragAndDropSupport(this.stagedTreeViewer, false);

		//add context menu support to the tree viewer
		this.stagedMenuFactory = new AbapGitStagingObjectMenuFactory(this.stagedTreeViewer, false, this, this.stagingUtil);
	}

	private void createStagedSectionToolbar() {
		Composite stagedToolbarComposite = this.toolkit.createComposite(this.stagedSection);
		stagedToolbarComposite.setBackground(null);
		RowLayoutFactory.fillDefaults().applyTo(stagedToolbarComposite);
		this.stagedSection.setTextClient(stagedToolbarComposite);

		this.stagedToolbarManager = new ToolBarManager(SWT.FLAT | SWT.HORIZONTAL);
		this.stagedToolbarManager.add(this.actionUnstage);
		this.stagedToolbarManager.add(new Separator());
		this.actionCollapseAllStaged = new CollapseAllAction(false);
		this.stagedToolbarManager.add(this.actionCollapseAllStaged);
		this.stagedToolbarManager.update(true);
		this.stagedToolbarManager.createControl(stagedToolbarComposite);
	}

	private TreeViewer createTreeViewer(Composite parent, Boolean unstaged) {
		TreeViewer viewer = this.createTree(parent);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(viewer.getControl());
		viewer.getTree().setData(FormToolkit.KEY_DRAW_BORDER, FormToolkit.TREE_BORDER);
		viewer.setLabelProvider(new AbapGitStagingLabelProvider());
		viewer.setContentProvider(new AbapGitStagingContentProvider());
		viewer.setComparator(new AbapGitStagingTreeComparator());
		viewer.getTree().setSortDirection(SWT.UP);
		if (this.treeFilter == null) {
			this.treeFilter = new AbapGitStagingTreeFilter();
		}
		viewer.setFilters(this.treeFilter);
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
		SWTUtil.setButtonWidthHint(this.commitAndPushButton);
		this.commitAndPushButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				validateInputAndPushChanges();
			}
		});
	}

	/**
	 * Creates a composite to display warnings and errors in commit section like
	 * invalid author, commit message etc..
	 */
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

		this.commitMessageTextViewer.getTextWidget().addModifyListener(e -> validateInputs());
	}

	private void createMarginPainter(TextViewer commitMessageTextViewer) {
		MarginPainter marginPainter = new MarginPainter(commitMessageTextViewer);
		marginPainter.setMarginRulerColumn(MAX_COMMIT_MESSAGE_LINE_LENGTH); //maximum recommended commit message line length is 72
		marginPainter.setMarginRulerColor(PlatformUI.getWorkbench().getDisplay().getSystemColor(SWT.COLOR_GRAY));
		commitMessageTextViewer.addPainter(marginPainter);
	}

	private void createAuthorComposite(Composite parent) {
		this.createPersonLabel(parent, null, Messages.AbapGitStaging_author);
		this.authorText = this.toolkit.createText(parent, null);
		this.authorText.setData(FormToolkit.KEY_DRAW_BORDER, FormToolkit.TREE_BORDER);
		this.authorText.setLayoutData(GridDataFactory.fillDefaults().indent(5, 0).grab(true, false).create());
		this.authorText.addModifyListener(e -> validateInputs());
	}

	private void createCommitterComposite(Composite parent) {
		this.createPersonLabel(parent, null, Messages.AbapGitStaging_committer);
		this.committerText = this.toolkit.createText(parent, null);
		this.committerText.setData(FormToolkit.KEY_DRAW_BORDER, FormToolkit.TREE_BORDER);
		this.committerText.setLayoutData(GridDataFactory.fillDefaults().indent(5, 0).grab(true, false).create());
		this.committerText.addModifyListener(e -> validateInputs());
	}

	private void createPersonLabel(Composite parent, Image image, String text) {
		Label imageLabel = new Label(parent, 0);
		imageLabel.setImage(image);
		Label textLabel = this.toolkit.createLabel(parent, text);
		textLabel.setForeground(this.toolkit.getColors().getColor(IFormColors.TB_TOGGLE));
	}

	/**
	 * Adds a drop down menu to action bar
	 */
	private void addDropDownMenuToActionBar() {
		IActionBars actionBars = getViewSite().getActionBars();
		IMenuManager dropdownMenu = actionBars.getMenuManager();

		dropdownMenu.setRemoveAllWhenShown(true);
		dropdownMenu.addMenuListener(new IMenuListener() {

			@Override
			public void menuAboutToShow(IMenuManager manager) {
				MenuManager subMenu = new MenuManager(Messages.AbapGitStagingView_GroupByMenu, null);
				subMenu.add(AbapGitStagingView.this.actionGroupByNone);

				if (AbapGitStagingView.this.stagingUtil.isGroupingObjectsSupported(AbapGitStagingView.this.model)) {
					subMenu.add(AbapGitStagingView.this.actionGroupByPackage);
					subMenu.add(AbapGitStagingView.this.actionGroupByTransport);
				}
				manager.add(subMenu);
			}
		});
	}

	/**
	 * Adds actions to the view toolbar
	 */
	private void contributeToActionBars() {
		IToolBarManager toolBarManager = getViewSite().getActionBars().getToolBarManager();
		//filter text box
		toolBarManager.add(createObjectsFilterText());
		//refresh action
		if (this.actionRefresh != null) {
			toolBarManager.add(this.actionRefresh);
		}
		//separator
		toolBarManager.add(new Separator());
		//change repository action
		if (this.actionSwitchRepository != null) {
			toolBarManager.add(this.actionSwitchRepository);
		}
		//separator
		toolBarManager.add(new Separator());
		//open linked package
		if (this.actionOpenPackage != null) {
			toolBarManager.add(this.actionOpenPackage);
		}
		//open repository in external browser
		if (this.actionOpenRepository != null) {
			toolBarManager.add(this.actionOpenRepository);
		}

		//Add drop-down-menu to Action bar
		addDropDownMenuToActionBar();
	}

	/**
	 * Adds a filter text box in the view toolbar
	 */
	private ControlContribution createObjectsFilterText() {
		//create filter text composite
		ControlContribution filterTextboxContribution = new ControlContribution("AbapGitStagingView.filterText") { //$NON-NLS-1$
			protected Control createControl(Composite parent) {
				Composite filterComposite = AbapGitStagingView.this.toolkit.createComposite(parent, 0);
				GridLayoutFactory.fillDefaults().numColumns(2).applyTo(filterComposite);
				filterComposite.setBackground(null);

				AbapGitStagingView.this.filterText = new Text(filterComposite, SWT.SEARCH | SWT.ICON_CANCEL | SWT.ICON_SEARCH);
				AbapGitStagingView.this.filterText.setMessage(Messages.AbapGitStaging_object_filter_text);
				GridData data = new GridData(SWT.LEFT, SWT.TOP, true, false);
				data.minimumWidth = 200;
				AbapGitStagingView.this.filterText.setLayoutData(data);

				AbapGitStagingView.this.filterText.addModifyListener(e -> applyFilter());
				return filterComposite;
			}
		};
		return filterTextboxContribution;
	}

	/**
	 * Applies filter on the treeviewers
	 */
	private void applyFilter() {
		String filterString = AbapGitStagingView.this.filterText.getText();
		AbapGitStagingView.this.treeFilter.setPattern(filterString);

		//get the expanded elements in the tree viewers
		Object[] unstagedExpanded = AbapGitStagingView.this.unstagedTreeViewer.getVisibleExpandedElements();
		Object[] stagedExpanded = AbapGitStagingView.this.stagedTreeViewer.getVisibleExpandedElements();
		setRedraw(false);
		try {
			refreshTreeViewers();
			//restore the tree expansion state
			AbapGitStagingView.this.unstagedTreeViewer.setExpandedElements(unstagedExpanded);
			AbapGitStagingView.this.stagedTreeViewer.setExpandedElements(stagedExpanded);
		} finally {
			setRedraw(true);
		}
		updateSectionHeaders();
	}

	private void setRedraw(boolean redraw) {
		this.unstagedTreeViewer.getControl().setRedraw(redraw);
		this.stagedTreeViewer.getControl().setRedraw(redraw);
	}

	private void refreshTreeViewers() {
		AbapGitStagingView.this.unstagedTreeViewer.refresh();
		AbapGitStagingView.this.stagedTreeViewer.refresh();
	}

	private void createActions() {
		//refresh repository action
		if (this.actionRefresh == null) {
			createRefreshAction();
		}
		//stage object action
		if (this.actionStage == null) {
			createStageAction();
		}
		//un-stage object action
		if (this.actionUnstage == null) {
			createUnstageAction();
		}
		//open linked package action
		if (this.actionOpenPackage == null) {
			createOpenPackageAction();
		}
		//open repository in external browser
		if (this.actionOpenRepository == null) {
			this.actionOpenRepository = new OpenRepositoryAction(this);
		}
		//change repository action
		if (this.actionSwitchRepository == null) {
			createRepositorySelectionToolbarAction();
		}

		//group by none action
		if (this.actionGroupByNone == null) {
			createGroupByNoneAction();
		}

		//group by package action
		if (this.actionGroupByPackage == null) {
			createGroupByPackageAction();
		}
		//group by transport action
		if (this.actionGroupByTransport == null) {
			createGroupByTransportAction();
		}
	}

	/**
	 * Action for not grouping staging objects under any nodes. AbapGitStaging
	 * objects are root level
	 *
	 * This action is the default action i.e. no grouping
	 */
	private void createGroupByNoneAction() {
		this.actionGroupByNone = new GroupByNoneAction();
	}

	/**
	 * Action for grouping staging objects under packages
	 */
	private void createGroupByPackageAction() {
		this.actionGroupByPackage = new GroupByPackageAction();
	}

	/**
	 * Action for grouping staging objects under transports
	 */
	private void createGroupByTransportAction() {
		this.actionGroupByTransport = new GroupByTransportAction();
	}

	/**
	 * Action for opening the package linked with the repository
	 */
	private void createOpenPackageAction() {
		this.actionOpenPackage = new OpenPackageAction(this);
	}

	/**
	 * Refresh AbapGit repository action
	 */
	private void createRefreshAction() {
		this.actionRefresh = new Action() {
			public void run() {
				if (AbapGitStagingView.this.repository != null && AbapGitStagingView.this.project != null) {
					fetchCredentialsAndRefresh();
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
		this.actionStage = new Action(Messages.AbapGitStaging_action_stage_xtol,
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
		this.actionUnstage = new Action(Messages.AbapGitStaging_action_unstage_xtol,
				AbstractUIPlugin.imageDescriptorFromPlugin(AbapGitUIPlugin.PLUGIN_ID, "icons/etool/unstage.png")) { //$NON-NLS-1$
			public void run() {
				IStructuredSelection selection = (IStructuredSelection) AbapGitStagingView.this.stagedTreeViewer.getSelection();
				unstageSelectedObjects(selection);
			}
		};
	}

	/**
	 * Action for switching a repository
	 */
	private void createRepositorySelectionToolbarAction() {
		this.actionSwitchRepository = new Action(Messages.AbapGitStaging_switch_repository, SWT.DROP_DOWN) {
			@Override
			public void runWithEvent(Event event) {
				Widget widget = event.widget;
				if (widget instanceof ToolItem) {
					ToolItem item = (ToolItem) widget;
					Rectangle bounds = item.getBounds();
					event.detail = SWT.ARROW;
					event.x = bounds.x;
					event.y = bounds.y + bounds.height;
					item.notifyListeners(SWT.Selection, event);
				}
			}
		};
		this.actionSwitchRepository
				.setImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin(AbapGitUIPlugin.PLUGIN_ID, "icons/obj/repository.png")); //$NON-NLS-1$
		this.actionSwitchRepository.setMenuCreator(new SwitchRepositoryMenuCreator(this, this.stagingUtil));
	}

	protected void stageSelectedObjects(IStructuredSelection selection) {
		List<Object> expandedNodes = new ArrayList<>();

		//In case no grouping is selected
		if (this.groupingSelection == Messages.AbapGitStagingView_GroupByNoneAction) {
			if (!AbapGitStagingView.singleFileStageMode) {
				ObjectStagingModeUtil.stageObjects(this.unstagedTreeViewer, selection, this.model, expandedNodes);
			} else {
				FileStagingModeUtil.stageObjects(this.unstagedTreeViewer, selection, this.model, expandedNodes);
			}
		}

		//In case grouping is selected
		if (this.groupingSelection == Messages.AbapGitStagingView_GroupByPackageAction
				|| this.groupingSelection == Messages.AbapGitStagingView_GroupByTransportAction) {
			if (AbapGitStagingView.singleFileStageMode) {
				FileStagingModeUtilForGroupNode.stageObjects(this.unstagedTreeViewer, selection, this.groupingModel, expandedNodes);
			}
			updateModelFromGroupingModel();
		}

		//update the tree viewers
		Object[] expandedElements = this.stagedTreeViewer.getExpandedElements();
		refreshUI();
		expandedNodes.addAll(Arrays.asList(expandedElements));
		this.stagedTreeViewer.setExpandedElements(expandedNodes.toArray());
	}

	private void updateModelFromGroupingModel() {
		this.model.getStagedObjects().getAbapgitobject().clear();
		this.model.getUnstagedObjects().getAbapgitobject().clear();

		for (IAbapGitStagingGroupNode stagedGroupNode : this.groupingModel.getStagedGroupObjects()) {
			this.model.getStagedObjects().getAbapgitobject().addAll(EcoreUtil.copyAll(stagedGroupNode.getAbapgitobjects()));
		}

		for (IAbapGitStagingGroupNode unStagedGroupNode : this.groupingModel.getUnstagedGroupObjects()) {
			this.model.getUnstagedObjects().getAbapgitobject().addAll(EcoreUtil.copyAll(unStagedGroupNode.getAbapgitobjects()));
		}
	}

	protected void unstageSelectedObjects(IStructuredSelection selection) {
		List<Object> expandedNodes = new ArrayList<>();

		//In case no grouping is selected
		if (this.groupingSelection == Messages.AbapGitStagingView_GroupByNoneAction) {
			if (!AbapGitStagingView.singleFileStageMode) {
				ObjectStagingModeUtil.unstageObjects(this.stagedTreeViewer, selection, this.model, expandedNodes);
			} else {
				FileStagingModeUtil.unstageObjects(this.stagedTreeViewer, selection, this.model, expandedNodes);
			}
		}

		//In case grouping is selected
		if (this.groupingSelection == Messages.AbapGitStagingView_GroupByPackageAction
				|| this.groupingSelection == Messages.AbapGitStagingView_GroupByTransportAction) {
			if (AbapGitStagingView.singleFileStageMode) {
				FileStagingModeUtilForGroupNode.unstageObjects(this.stagedTreeViewer, selection, this.groupingModel, expandedNodes);
			}
			updateModelFromGroupingModel();
		}

		//update the tree viewers
		Object[] expandedElements = this.unstagedTreeViewer.getExpandedElements();
		refreshUI();
		expandedNodes.addAll(Arrays.asList(expandedElements));
		this.unstagedTreeViewer.setExpandedElements(expandedNodes.toArray());
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
		if (!isLoggedOn(project)) {
			return;
		}

		//if a new project, refresh the services
		if(this.project != project) {
			AbapGitStagingView.this.project = project;
			AbapGitStagingView.this.destinationId = this.stagingUtil.getDestination(project);
			this.projectChangeListener.setProject(project);
			this.repoService = getRepositoryService(this.destinationId);
			if (this.repoService == null) { //AbapGit not supported
				MessageDialog.openError(getSite().getShell(),
						NLS.bind(Messages.AbapGitView_not_supported, AbapGitStagingView.this.project.getName()),
						NLS.bind(Messages.AbapGitView_not_supported, AbapGitStagingView.this.project.getName()));
				return;
			}
			this.repoExternaService = getExternalRepositoryService(new NullProgressMonitor());
		}

		AbapGitStagingView.this.repository = repository;
		//load staging data and refresh UI
		if (fetchCredentialsAndRefresh().equals(Status.CANCEL_STATUS)) {
			//reset if user cancels refresh
			AbapGitStagingView.this.project = null;
			AbapGitStagingView.this.repository = null;
		}
	}

	private IStatus fetchCredentialsAndRefresh() {
		if (this.repositoryExternalInfo == null) {
			fetchExternalRepositoryInfo();
		}
		if (this.repositoryExternalInfo != null) {
			//get credentials from user if the repository is private
			if (checkIfCredentialsRequired()) {
				this.repositoryCredentials = getRepoCredentialsFromSecureStorage(this.repository.getUrl());

				if (this.repositoryCredentials == null) {
					this.credsRetrievedFromSecureStorage = false;

					if (getGitCredentials().equals(Status.CANCEL_STATUS)) {
						return Status.CANCEL_STATUS;
					}
				} else {
					this.credsRetrievedFromSecureStorage = true;

				}
			}
			refresh();
		}
		return Status.OK_STATUS;
	}

	/**
	 * Refresh/Load the staging contents from the back-end and updates the UI
	 */
	private void refresh() {
		Job refreshJob = new Job(Messages.AbapGitStaging_task_fetch_repos_staging) {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				try {
					//perform repository checks
					repositoryChecks(monitor);

					//fetch the staging data
					monitor.beginTask(Messages.AbapGitStaging_task_fetch_repos_staging, IProgressMonitor.UNKNOWN);
					AbapGitStagingView.this.model = AbapGitStagingView.this.repoService
							.stage(AbapGitStagingView.this.repository, AbapGitStagingView.this.repositoryCredentials, monitor);
					if (AbapGitStagingView.this.model != null) {
						//If grouping is selected, group the objects from model
						if (AbapGitStagingView.this.groupingSelection.equals(Messages.AbapGitStagingView_GroupByPackageAction)
								|| AbapGitStagingView.this.groupingSelection.equals(Messages.AbapGitStagingView_GroupByTransportAction)) {

							AbapGitStagingView.this.groupingModel = AbapGitStagingGroupingUtil.groupObjects(AbapGitStagingView.this.model,
									AbapGitStagingView.this.groupingSelection);
						}
						//refresh UI
						refreshUI();
					}
				} catch (CommunicationException | ResourceException | OperationCanceledException e) {
					handleException(e, stage);
				} catch (OutDatedClientException e) {
					AdtUtilUiPlugin.getDefault().getAdtStatusService().handle(e, null);
				}
				return Status.OK_STATUS;
			}
		};

		IProgressService service = PlatformUI.getWorkbench().getProgressService();
		service.showInDialog(getSite().getShell(), refreshJob);
		refreshJob.schedule();
	}

	/**
	 * Refreshes the staging view
	 */
	private void refreshUI() {
		Display.getDefault().syncExec(() -> {
			//set form header
			AbapGitStagingView.this.mainForm.setText(RepositoryUtil.getRepoNameFromUrl(AbapGitStagingView.this.repository.getUrl()) + " [" //$NON-NLS-1$
					+ RepositoryUtil.getBranchNameFromRef(AbapGitStagingView.this.repository.getBranchName()) + "]" + " [" //$NON-NLS-1$//$NON-NLS-2$
					+ this.project.getName() + "]"); //$NON-NLS-1$

			AbapGitStagingView.this.unstagedTreeViewerInput.clear();
			AbapGitStagingView.this.stagedTreeViewerInput.clear();

			if (this.groupingSelection.equals(Messages.AbapGitStagingView_GroupByPackageAction)
					|| this.groupingSelection.equals(Messages.AbapGitStagingView_GroupByTransportAction)) {
				AbapGitStagingView.this.unstagedTreeViewerInput
						.addAll(this.groupingModel.getUnstagedGroupObjects());
				AbapGitStagingView.this.stagedTreeViewerInput
						.addAll(this.groupingModel.getStagedGroupObjects());
			}
			else {
				AbapGitStagingView.this.unstagedTreeViewerInput.addAll(getUnstagedObjectsFromModel(AbapGitStagingView.this.model));
				AbapGitStagingView.this.stagedTreeViewerInput.addAll(getStagedObjectsFromModel(AbapGitStagingView.this.model));
			}

			//update the tree viewers
			this.unstagedTreeViewer.getControl().setRedraw(false);
			this.stagedTreeViewer.getControl().setRedraw(false);

			AbapGitStagingView.this.unstagedTreeViewer.refresh();
			AbapGitStagingView.this.stagedTreeViewer.refresh();

			this.unstagedTreeViewer.getControl().setRedraw(true);
			this.stagedTreeViewer.getControl().setRedraw(true);

			//update the commit message section
			if (this.model.getCommitMessage() != null) {
				if (this.model.getCommitMessage().getAuthor() != null) {
					AbapGitStagingView.this.authorText.setText(AbapGitStagingView.this.model.getCommitMessage().getAuthor().getName() + " <" //$NON-NLS-1$
							+ AbapGitStagingView.this.model.getCommitMessage().getAuthor().getEmail() + ">"); //$NON-NLS-1$
				}
				if (this.model.getCommitMessage().getCommitter() != null) {
					AbapGitStagingView.this.committerText
							.setText(AbapGitStagingView.this.model.getCommitMessage().getCommitter().getName() + " <" //$NON-NLS-1$
									+ AbapGitStagingView.this.model.getCommitMessage().getCommitter().getEmail() + ">"); //$NON-NLS-1$
				}
			}

			updateSectionHeaders();
			updateButtonsState();
			validateInputs();
		});
	}

	private boolean isLoggedOn(IProject project) {
		return this.stagingUtil.ensureLoggedOn(project);
	}

	private boolean checkIfCredentialsRequired() {
		return GitCredentialsService.checkIfCredentialsRequired(this.repositoryExternalInfo);
	}

	private void fetchExternalRepositoryInfo() {
		try {
			IRunnableWithProgress runnable = new IRunnableWithProgress() {
				@Override
				public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
					monitor.beginTask(Messages.AbapGitWizardPageRepositoryAndCredentials_task_fetch_repo_info, IProgressMonitor.UNKNOWN);
					AbapGitStagingView.this.repositoryExternalInfo = AbapGitStagingView.this.repoExternaService
							.getExternalRepositoryInfo(AbapGitStagingView.this.repository.getUrl(), null, null, monitor);
				}
			};
			new ProgressMonitorDialog(getSite().getShell()).run(true, true, runnable);
		} catch (InvocationTargetException e) {
			StatusManager.getManager().handle(new Status(IStatus.ERROR, AbapGitUIPlugin.PLUGIN_ID,
					Messages.AbapGitView_task_fetch_repos_error, e.getTargetException()), StatusManager.SHOW);
		} catch (InterruptedException e) {
			AbapGitUIPlugin.getDefault().getLog().log(new Status(IStatus.ERROR, AbapGitUIPlugin.PLUGIN_ID, e.getMessage(), e));
		}
	}

	private IExternalRepositoryInfoService getExternalRepositoryService(IProgressMonitor monitor) {
		return RepositoryServiceFactory.createExternalRepositoryInfoService(AbapGitStagingView.this.destinationId, monitor);
	}

	/**
	 * Resets the staging view
	 */
	public void resetStagingView() {
		resetModel();
		resetUI();
	}

	private void resetModel() {
		this.model = null;
		this.repository = null;
		this.repositoryExternalInfo = null;
		this.repositoryCredentials = null;
		this.groupingModel = null;
	}

	private void resetUI() {
		this.mainForm.setText(Messages.AbapGitStaging_no_repository_selected);
		this.commitMessageTextViewer.getTextWidget().setText(""); //$NON-NLS-1$
		this.authorText.setText(""); //$NON-NLS-1$
		this.committerText.setText(""); //$NON-NLS-1$
		resetTreeFilter();

		this.unstagedTreeViewerInput.clear();
		this.unstagedTreeViewer.setInput(this.unstagedTreeViewerInput);
		this.stagedTreeViewerInput.clear();
		this.stagedTreeViewer.setInput(this.stagedTreeViewerInput);

		updateSectionHeaders();
		updateButtonsState();

		//Update the default grouping to NONE
		this.groupingSelection = Messages.AbapGitStagingView_GroupByNoneAction;
		this.actionGroupByPackage.setChecked(false);
		this.actionGroupByTransport.setChecked(false);
		this.actionGroupByNone.setChecked(true);
	}

	private void resetTreeFilter() {
		this.filterText.setText(""); //$NON-NLS-1$
		this.treeFilter.setPattern(""); //$NON-NLS-1$
	}

	/**
	 * Updates the header text for unstaged and staged changes section with the
	 * tree item count
	 */
	private void updateSectionHeaders() {
		if (!isFilterActive()) {
			//update the unstaged section header
			this.unstagedSection.setText(Messages.AbapGitStaging_unstaged_changes_section_header + " (" //$NON-NLS-1$
					+ this.unstagedTreeViewer.getTree().getItemCount() + ")"); //$NON-NLS-1$
			//update the staged section header
			this.stagedSection.setText(
					Messages.AbapGitStaging_staged_changes_section_header + " (" + this.stagedTreeViewer.getTree().getItemCount() + ")"); //$NON-NLS-1$ //$NON-NLS-2$
		} else {
			//update the unstaged section header
			this.unstagedSection.setText(Messages.AbapGitStaging_unstaged_changes_section_header + " (" //$NON-NLS-1$
					+ this.unstagedTreeViewer.getTree().getItemCount() + "/" + getObjectsCount(true) + ")"); //$NON-NLS-1$ //$NON-NLS-2$
			//update the staged section header
			this.stagedSection.setText(Messages.AbapGitStaging_staged_changes_section_header + " (" //$NON-NLS-1$
					+ this.stagedTreeViewer.getTree().getItemCount() + "/" + getObjectsCount(false) + ")"); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	/**
	 * Updates the state of UI buttons
	 */
	private void updateButtonsState() {
		this.commitAndPushButton.setEnabled(this.stagedTreeViewer.getTree().getItemCount() > 0 ? true : false); //disabled if no staged changes
		this.actionStage.setEnabled(this.unstagedTreeViewer.getTree().getItemCount() > 0 ? true : false);
		this.actionCollapseAllUnstaged.setEnabled(this.unstagedTreeViewer.getTree().getItemCount() > 0 ? true : false);
		this.actionUnstage.setEnabled(this.stagedTreeViewer.getTree().getItemCount() > 0 ? true : false);
		this.actionCollapseAllStaged.setEnabled(this.stagedTreeViewer.getTree().getItemCount() > 0 ? true : false);
		this.actionRefresh.setEnabled(this.repository == null ? false : true);
		this.actionOpenPackage.setEnabled(this.repository == null ? false : true);
		this.actionOpenRepository.setEnabled(this.repository == null ? false : true);
	}

	private List<IAbapGitObject> getUnstagedObjectsFromModel(IAbapGitStaging staging) {
		List<IAbapGitObject> objects = staging.getUnstagedObjects().getAbapgitobject();
		return objects;
	}

	private List<IAbapGitObject> getStagedObjectsFromModel(IAbapGitStaging staging) {
		List<IAbapGitObject> objects = staging.getStagedObjects().getAbapgitobject();
		return objects;
	}

	private int getObjectsCount(boolean unstaged) {
		if (unstaged && this.model.getUnstagedObjects() == null) {
			return 0;
		} else if (!unstaged && this.model.getStagedObjects() == null) {
			return 0;
		}
		return unstaged ? this.model.getUnstagedObjects().getAbapgitobject().size()
				: this.model.getStagedObjects().getAbapgitobject().size();
	}

	/**
	 * Validates the UI inputs
	 */
	protected int validateInputs() {
		int errorCode = 0;
		boolean valid = true;

		//check commit message
		String commitMessage = this.commitMessageTextViewer.getTextWidget().getText();
		IDocument commitMessageDocument = new Document(commitMessage);
		if (commitMessageDocument.getNumberOfLines() > 1) {
			try {
				IRegion lineInfo = commitMessageDocument.getLineInformation(1);
				if (lineInfo.getLength() > 0) {
					valid = false;
					errorCode = 1;
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
			errorCode = 2;
		}

		//check committer
		String committerText = this.committerText.getText();
		if (getCommitterFromUIText(committerText) == null) {
			setWarnings(Messages.AbapGitStaging_invalid_committer);
			valid = false;
			errorCode = 3;
		}

		if (valid) {
			hideWarnings();
		}

		return errorCode;
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

	private void validateInputAndPushChanges() {
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

		if (this.model.getCommitMessage() == null) {
			//commit message object will be null when backend does not sent the author and committer details
			//so create a new instance of commit message and fill the necessary details
			IAbapGitCommitMessage commitMessageModel = IAbapgitstagingFactory.eINSTANCE.createAbapGitCommitMessage();
			this.model.setCommitMessage(commitMessageModel);
		}

		//update model
		this.model.getCommitMessage().setMessage(commitMessage);
		this.model.getCommitMessage().setAuthor(author);
		this.model.getCommitMessage().setCommitter(committer);

		fetchCredentialsAndCommit();
	}

	private IStatus getGitCredentials(String previousErrorMessage) {
		//get credentials
		if (this.repositoryCredentials == null) {
			this.repositoryCredentials = GitCredentialsService.getCredentialsFromUser(getSite().getShell(), this.repository.getUrl(), previousErrorMessage);
			if (this.repositoryCredentials == null) {
				return Status.CANCEL_STATUS;
			}
		}
		return Status.OK_STATUS;
	}

	private IStatus getGitCredentials() {
		return getGitCredentials(null);
	}

	private void fetchCredentialsAndCommit() {
		this.repositoryCredentials = getRepoCredentialsFromSecureStorage(this.repository.getUrl());

		if (this.repoService.getURIFromAtomLink(this.repository, IRepositoryService.RELATION_CHECK) == null) {
			//compatibility handling for credentials checks for 1911
			//TODO: remove this check once customers upgrade to 2002
			IExternalRepositoryInfoRequest credentials;
			if (this.repositoryCredentials != null) {
				this.credsRetrievedFromSecureStorage = true;
				credentials = this.repositoryCredentials;
			} else {
				this.credsRetrievedFromSecureStorage = false;
				credentials = GitCredentialsService.getCredentialsFromUser(getSite().getShell(), null);
				if (credentials == null) {
					return;
				}
			}
			commitAndPush(credentials);
		}
		//from 2002 release, we have a better way of handling the credentials, so no need to always read the credentials before commit
		//if the user has entered credentials while doing the previous commit, the credentials will be validated before we do a push
		//hence we can always rely on the value of the variable repositoryCredentials
		else {

			if(this.repositoryCredentials == null) {
				this.credsRetrievedFromSecureStorage = false;
			} else {
				this.credsRetrievedFromSecureStorage = true;
			}

			if (getGitCredentials().equals(Status.OK_STATUS)) {
				commitAndPush(this.repositoryCredentials);
			}
		}
	}

	private void commitAndPush(IExternalRepositoryInfoRequest credentials) {
		//push
		Job pushJob = new Job(Messages.AbapGitStaging_push_job_title) {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				try {
					if (credentials != null) {
						AbapGitStagingView.this.repositoryCredentials = credentials;
					}
					//perform repository checks
					repositoryChecks(monitor);

					//push changes
					monitor.beginTask(Messages.AbapGitStaging_push_job_title, IProgressMonitor.UNKNOWN);
					AbapGitStagingView.this.repoService.push(new NullProgressMonitor(), AbapGitStagingView.this.model,
							AbapGitStagingView.this.repositoryCredentials,
							AbapGitStagingView.this.repository);

					//refresh
					Display.getDefault().syncExec(() -> {
						MessageDialog.openInformation(getSite().getShell(), Messages.AbapGitStaging_push_job_successful,
								Messages.AbapGitStaging_push_job_successful_desc);
						//clear commit message
						AbapGitStagingView.this.commitMessageTextViewer.getTextWidget().setText(""); //$NON-NLS-1$
						//clear staged changes
						AbapGitStagingView.this.stagedTreeViewerInput.clear();
						AbapGitStagingView.this.model.getStagedObjects().getAbapgitobject().clear();
						AbapGitStagingView.this.stagedTreeViewer.refresh();
						//update view
						updateButtonsState();
						updateSectionHeaders();
					});
					return Status.OK_STATUS;
				} catch (CommunicationException | ResourceException | OperationCanceledException e) {
					handleException(e, push);
				} catch (OutDatedClientException e) {
					AdtUtilUiPlugin.getDefault().getAdtStatusService().handle(e, null);
				}
				return Status.CANCEL_STATUS;
			}
		};

		IProgressService service = PlatformUI.getWorkbench().getProgressService();
		service.showInDialog(getSite().getShell(), pushJob);
		pushJob.schedule();
	}

	private void repositoryChecks(IProgressMonitor monitor) {
		//perform repository checks
		if (this.repoService.getURIFromAtomLink(this.repository, IRepositoryService.RELATION_CHECK) != null) {
			monitor.beginTask(Messages.AbapGitStaging_check_job_title, IProgressMonitor.UNKNOWN);
			AbapGitStagingView.this.repoService.repositoryChecks(monitor, this.repositoryCredentials, AbapGitStagingView.this.repository);
		}
	}

	@Override
	public void setFocus() {
		this.unstagedTreeViewer.getControl().setFocus();
	}

	private void handleException(Exception e, final String action) {
		//invalid credentials : this condition check is only valid from 2002
		if (e instanceof ResourceException && GitCredentialsService.isAuthenticationIssue((ResourceException) e)) {
			AbapGitStagingView.this.repositoryCredentials = null;

			String message = e.getLocalizedMessage();
			//If credentials not retrieved from secure storage, show appropriate Error Dialog
			//Otherwise simply show the credentials dialog
			if (this.credsRetrievedFromSecureStorage == false) {
				ErrorHandlingService.handleExceptionAndDisplayInErrorDialog(e, getSite().getShell());
			} else {
				GitCredentialsService.deleteCredentialsFromSecureStorage(this.repository.getUrl());
				message = null;
			}
			this.credsRetrievedFromSecureStorage = false;

			if (getGitCredentials(message).equals(Status.OK_STATUS)) {
				//re-trigger action
				if (action.equals(stage)) {
					refresh();
				} else if (action.equals(push)) {
					commitAndPush(this.repositoryCredentials);
				}
			}
		} else {
			String excMessage = null;
			//handle the error code 409 from back-end ( HTTP 409 is the error code returned by the back-end in case of process
			//conflicts eg: triggering a push while a push is already running ). In such case the exception text is part of the response body
			if (e instanceof ResourceException && ((ResourceException) e).getStatus() == 409) {
				excMessage = getExceptionTextFromResponse((ResourceException) e);
			}
			if (excMessage == null || excMessage.isEmpty()) {
				excMessage = e.getMessage();
				//compatibility handling for credentials checks for 1911
				//TODO: remove this check once customers upgrade to 2002
				if (this.repoService.getURIFromAtomLink(this.repository, IRepositoryService.RELATION_CHECK) == null) {
					AbapGitStagingView.this.repositoryCredentials = null;
				}
			}

			if (action.equals(push)) {
				ErrorHandlingService.openErrorDialog(Messages.AbapGitStaging_push_job_error, excMessage, getSite().getShell(), true);
			} else if (action.equals(stage)) {
				ErrorHandlingService.openErrorDialog(Messages.AbapGitStaging_task_fetch_repos_staging_error, excMessage,
						getSite().getShell(), true);
			}
		}
	}

	private static String getExceptionTextFromResponse(ResourceException exception) {
		//Exception text in case of 409 error is part of the response body
		try {
			InputStream inputStream = exception.getResponse().getBody().getContent();
			ByteArrayOutputStream result = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int length;
			while ((length = inputStream.read(buffer)) != -1) {
				result.write(buffer, 0, length);
			}
			return result.toString(StandardCharsets.UTF_8.name());
		} catch (IOException e) {
			return null;
		}
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
		viewer.addDoubleClickListener((event) -> {
			//open the object
			if (event.getSelection() instanceof IStructuredSelection) {
				IStructuredSelection selection = (IStructuredSelection) event.getSelection();
				Object object = selection.getFirstElement();
				this.stagingUtil.openEditor(object, this.project);
			}
		});
	}

	private void addKeyListener(TreeViewer viewer, boolean unstaged) {
		viewer.getTree().addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				//add key listener for text copy
				if (e.keyCode == KEY_STROKE_COPY.getNaturalKey() && e.stateMask == KEY_STROKE_COPY.getModifierKeys()) {
					if (unstaged) {
						new CopyNameAction(AbapGitStagingView.this.unstagedTreeViewer).run();
					} else {
						new CopyNameAction(AbapGitStagingView.this.stagedTreeViewer).run();
					}
				}
			}
		});
	}

	private IRepositoryService getRepositoryService(String destinationId) {
		return RepositoryServiceFactory.createRepositoryService(destinationId, new NullProgressMonitor());
	}

	private IAbapGitStagingService getStagingUtil() {
		if (this.stagingUtil == null) {
			this.stagingUtil = AbapGitUIServiceFactory.createAbapGitStagingService();
		}
		return this.stagingUtil;
	}

	/**
	 * Returns whether the tree filter is currently active or not
	 */
	private boolean isFilterActive() {
		String filter = this.filterText.getText();
		if (filter.isEmpty()) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public void dispose() {
		super.dispose();
		this.unstagedMenuFactory.dispose();
		this.stagedMenuFactory.dispose();
		ResourcesPlugin.getWorkspace().removeResourceChangeListener(this.projectChangeListener);
	}

	public IProject getProject() {
		return this.project;
	}

	public IRepository getRepository() {
		return this.repository;
	}

	//actions
	private class CollapseAllAction extends Action {
		private final boolean unstaged;

		CollapseAllAction(boolean unstaged) {
			super(Messages.AbapGitStaging_collapse_all_xbut);
			this.unstaged = unstaged;
		}

		@Override
		public ImageDescriptor getImageDescriptor() {
			return PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_ELCL_COLLAPSEALL);
		}

		@Override
		public void run() {
			if (this.unstaged) {
				AbapGitStagingView.this.unstagedTreeViewer.collapseAll();
			} else {
				AbapGitStagingView.this.stagedTreeViewer.collapseAll();
			}
		}
	}

	@Override
	public IExternalRepositoryInfo getExternalRepositoryInfo() {
		return this.repositoryExternalInfo;
	}

	/**
	 * Retrieves the credentials for a given repository from the secure storage
	 * if it exists. This method is not moved to GitCredentialsService, as it
	 * should not be a public method
	 *
	 * @param repositoryURL
	 * @return the credentials from Secure Store for the given repository url
	 */
	private static IExternalRepositoryInfoRequest getRepoCredentialsFromSecureStorage(String repositoryURL) {
		if (repositoryURL == null) {
			return null;
		}
		ISecurePreferences preferences = SecurePreferencesFactory.getDefault();
		String slashEncodedURL = GitCredentialsService.getUrlForNodePath(repositoryURL);
		if (slashEncodedURL != null && preferences != null && preferences.nodeExists(slashEncodedURL)) {
			ISecurePreferences node = preferences.node(slashEncodedURL);
			IExternalRepositoryInfoRequest credentials = AbapgitexternalrepoFactoryImpl.eINSTANCE.createExternalRepositoryInfoRequest();

			try {
				credentials.setUser(node.get("user", null)); //$NON-NLS-1$
				credentials.setPassword(node.get("password", null)); //$NON-NLS-1$
			} catch (StorageException e) {
				AbapGitUIPlugin.getDefault().getLog().log(new Status(IStatus.ERROR, AbapGitUIPlugin.PLUGIN_ID, e.getMessage(), e));
				return null;
			}

			if (credentials.getPassword() == null || credentials.getUser() == null) {
				return null;
			}
			credentials.setUrl(repositoryURL);
			return credentials;
		}

		return null;
	}

	public class GroupByNoneAction extends Action {

		protected GroupByNoneAction() {
			super(Messages.AbapGitStagingView_GroupByNoneAction, Action.AS_RADIO_BUTTON);
		}

		@Override
		public void run() {
			AbapGitStagingView.this.groupingSelection = Messages.AbapGitStagingView_GroupByNoneAction;

			refreshUI();

			AbapGitStagingView.this.unstagedTreeViewer.setInput(AbapGitStagingView.this.unstagedTreeViewerInput);
			AbapGitStagingView.this.stagedTreeViewer.setInput(AbapGitStagingView.this.stagedTreeViewerInput);

		}

	}

	public class GroupByPackageAction extends Action {

		protected GroupByPackageAction() {
			super(Messages.AbapGitStagingView_GroupByPackageAction, Action.AS_RADIO_BUTTON);
		}

		@Override
		public void run() {
			//Set the grouping selection to packages.
			//TODO May be this is not needed and we can retrieve selection from Group By Action
			AbapGitStagingView.this.groupingSelection = Messages.AbapGitStagingView_GroupByPackageAction;

			//Group objects under packages
			AbapGitStagingView.this.groupingModel = AbapGitStagingGroupingUtil.groupObjects(AbapGitStagingView.this.model,
					AbapGitStagingView.this.groupingSelection);

			//Refresh the UI
			refreshUI();

			//Set the input
			AbapGitStagingView.this.unstagedTreeViewer.setInput(AbapGitStagingView.this.unstagedTreeViewerInput);
			AbapGitStagingView.this.stagedTreeViewer.setInput(AbapGitStagingView.this.stagedTreeViewerInput);


		}

	}

	public class GroupByTransportAction extends Action {

		protected GroupByTransportAction() {
			super(Messages.AbapGitStagingView_GroupByTransportAction, Action.AS_RADIO_BUTTON);
		}

		@Override
		public void run() {
			AbapGitStagingView.this.groupingSelection = Messages.AbapGitStagingView_GroupByTransportAction;

			//Group objects under transports
			AbapGitStagingView.this.groupingModel = AbapGitStagingGroupingUtil.groupObjects(AbapGitStagingView.this.model,
					AbapGitStagingView.this.groupingSelection);

			//Refresh the UI
			refreshUI();

			//Set the input
			AbapGitStagingView.this.unstagedTreeViewer.setInput(AbapGitStagingView.this.unstagedTreeViewerInput);
			AbapGitStagingView.this.stagedTreeViewer.setInput(AbapGitStagingView.this.stagedTreeViewerInput);
		}
	}

}
