package org.abapgit.adt.ui.internal.wizards;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.abapgit.adt.backend.model.agitpullmodifiedobjects.IOverwriteObject;
import org.abapgit.adt.ui.internal.i18n.Messages;
import org.abapgit.adt.ui.internal.repositories.IRepositoryModifiedObjects;
import org.abapgit.adt.ui.internal.repositories.RepositoryModifiedObjects;
import org.abapgit.adt.ui.internal.util.RepositoryUtil;
import org.eclipse.jface.layout.TreeColumnLayout;
import org.eclipse.jface.viewers.AbstractTreeViewer;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.ICheckStateProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ScrollBar;
import org.eclipse.swt.widgets.Text;

/**
 * Wizard page showing modified objects for pull with lazy loading and debounced
 * async search filter (optimized and non-blocking for large datasets).
 */
public class AbapGitWizardPageObjectsSelectionForPull extends WizardPage {

	private static final String PAGE_NAME = AbapGitWizardPageObjectsSelectionForPull.class.getName();

	private final Set<IRepositoryModifiedObjects> repoToModifiedObjects;
	protected CheckboxTreeViewer modifiedObjTreeViewer;
	private Composite container;
	private Object[] selectedObjectsForRepository;
	private TreeColumnLayout treeColumnLayout;

	private final Map<IRepositoryModifiedObjects, Integer> repoOffsets = new HashMap<>();

	// Holds filtered results during search without changing repo data
	private final Map<IRepositoryModifiedObjects, List<IOverwriteObject>> searchFilteredChildren = new HashMap<>();

	// Search-related
	private Text searchText;
	// single-thread scheduled executor for debounce; heavy work uses background thread logic
	private final ScheduledExecutorService searchExecutor = Executors.newSingleThreadScheduledExecutor();
	private ScheduledFuture<?> pendingSearch;
	private volatile boolean cancelFiltering = false;
	private String lastQuery = ""; //$NON-NLS-1$

	// for avoiding unnecessary UI updates
	private final Set<IRepositoryModifiedObjects> lastFilteredRepos = new HashSet<>();

	// debounce delay (ms)
	private static final long DEBOUNCE_MS = 400L;

	// yield interval to allow the background thread to breathe
	private static final int YIELD_REPO_INTERVAL = 20;

	// safety cap to avoid overwhelming the viewer with too many results
	private static final int MAX_MATCHED_OBJECTS = 5000;

	public AbapGitWizardPageObjectsSelectionForPull(Set<IRepositoryModifiedObjects> repoToModifiedOverwriteObjects, String message,
			int messageType) {
		super(PAGE_NAME);
		setTitle(Messages.AbapGitWizardPageObjectsSelectionForPull_Title);
		setMessage(message, messageType);
		this.repoToModifiedObjects = repoToModifiedOverwriteObjects;
	}

	@Override
	public void createControl(Composite parent) {
		Composite root = new Composite(parent, SWT.NONE);
		root.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		root.setLayout(new org.eclipse.swt.layout.GridLayout(1, false));

		addSearchBar(root);

		this.container = new Composite(root, SWT.NONE);
		this.treeColumnLayout = new TreeColumnLayout();
		this.container.setLayout(this.treeColumnLayout);
		this.container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		this.modifiedObjTreeViewer = new CheckboxTreeViewer(this.container,
				SWT.MULTI | SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FILL);

		this.modifiedObjTreeViewer.setContentProvider(new ModifiedObjectTreeContentProvider());
		this.modifiedObjTreeViewer.getTree().setFocus();
		this.modifiedObjTreeViewer.getTree().setHeaderVisible(true);
		this.modifiedObjTreeViewer.getTree().setLinesVisible(true);
		this.modifiedObjTreeViewer.setAutoExpandLevel(AbstractTreeViewer.ALL_LEVELS);

		createColumns();

		this.modifiedObjTreeViewer.setInput(this.repoToModifiedObjects);

		setControl(root);
		setPageComplete(true);

		addListeners();
	}

	private void addSearchBar(Composite parent) {
		Composite searchComposite = new Composite(parent, SWT.NONE);
		searchComposite.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
		searchComposite.setLayout(new FillLayout(SWT.HORIZONTAL));

		this.searchText = new Text(searchComposite, SWT.SEARCH | SWT.ICON_SEARCH | SWT.CANCEL);
		this.searchText.setMessage("Search objects..."); //$NON-NLS-1$

		this.searchText.addModifyListener(e -> {
			final String query = this.searchText.getText().trim().toLowerCase();

			// Avoid scheduling same query repeatedly
			if (query.equals(this.lastQuery)) {
				return;
			}

			// signal running filter to stop
			this.cancelFiltering = true;

			// cancel previous scheduled task, if any
			if (this.pendingSearch != null && !this.pendingSearch.isDone()) {
				this.pendingSearch.cancel(false);
			}

			// schedule new search after debounce delay; scheduled task will clear cancel flag then run
			this.pendingSearch = this.searchExecutor.schedule(() -> {
				// allow new filter to proceed
				this.cancelFiltering = false;
				runSearchFilter(query);
			}, DEBOUNCE_MS, TimeUnit.MILLISECONDS);
		});
	}

	/**
	 * Performs filtering off the UI thread, cooperatively cancellable, yields
	 * periodically, updates UI only when necessary and avoids heavy expandAll()
	 * calls.
	 */
	private void runSearchFilter(String query) {
		// quick-return: if query unchanged because something else set it, skip
		// (we'll still set lastQuery at the end to reflect latest executed)
		// If query empty - restore full view on UI thread
		if (query.isEmpty()) {
			org.eclipse.swt.widgets.Display.getDefault().asyncExec(() -> {
				synchronized (this.searchFilteredChildren) {
					this.searchFilteredChildren.clear();
				}

				// Reset offsets so initial 100 children load again
				this.repoOffsets.clear();

				// Refresh viewer back to initial lazy-load state
				if (!this.modifiedObjTreeViewer.getControl().isDisposed()) {
					this.modifiedObjTreeViewer.getControl().setRedraw(false);
					try {
						this.modifiedObjTreeViewer.setInput(this.repoToModifiedObjects);
						this.modifiedObjTreeViewer.refresh();

						// Collapse all so the user sees the top-level repos again
						this.modifiedObjTreeViewer.collapseAll();
					} finally {
						this.modifiedObjTreeViewer.getControl().setRedraw(true);
					}
				}
			});
			synchronized (this.lastFilteredRepos) {
				this.lastFilteredRepos.clear();
			}
			this.lastQuery = query;
			return;
		}


		// Build filteredMap in background thread cooperatively to allow cancellation and yielding
		final Map<IRepositoryModifiedObjects, List<IOverwriteObject>> filteredMap = new HashMap<>();
		int processedRepos = 0;
		int matchedObjects = 0;

		for (IRepositoryModifiedObjects repo : this.repoToModifiedObjects) {
			// cooperative cancellation
			if (this.cancelFiltering) {
				return;
			}

			List<IOverwriteObject> all = repo.getModifiedObjects();
			if (all == null || all.isEmpty()) {
				processedRepos++;
				// periodic yield
				if (processedRepos % YIELD_REPO_INTERVAL == 0 && !this.cancelFiltering) {
					try {
						Thread.sleep(1);
					} catch (InterruptedException ex) {
						return;
					}
				}
				continue;
			}

			List<IOverwriteObject> filtered = new ArrayList<>();
			for (IOverwriteObject obj : all) {
				if (this.cancelFiltering) {
					return;
				}

				// lowercase once per field
				String name = obj.getName() != null ? obj.getName().toLowerCase() : ""; //$NON-NLS-1$
				String pkg = obj.getPackageName() != null ? obj.getPackageName().toLowerCase() : ""; //$NON-NLS-1$
				String type = obj.getType() != null ? obj.getType().toLowerCase() : ""; //$NON-NLS-1$

				if (name.contains(query) || pkg.contains(query) || type.contains(query)) {
					filtered.add(obj);
					matchedObjects++;
				}

				// If total matched objects exceed cap, stop collecting more
				if (matchedObjects >= MAX_MATCHED_OBJECTS) {
					break;
				}
			}

			if (!filtered.isEmpty()) {
				filteredMap.put(repo, filtered);
			}

			processedRepos++;

			// periodic yield to avoid monopolizing CPU for very large repo sets
			if (processedRepos % YIELD_REPO_INTERVAL == 0) {
				if (this.cancelFiltering) {
					return;
				}
				try {
					Thread.sleep(1);
				} catch (InterruptedException ex) {
					return;
				}
			}

			// If matchedObjects cap hit, stop filtering more repos
			if (matchedObjects >= MAX_MATCHED_OBJECTS) {
				break;
			}
		}

		// prepare set of repos that have matches
		final Set<IRepositoryModifiedObjects> filteredRepos = filteredMap.keySet();

		// Determine if UI update is necessary (avoid redundant setInput/refresh)
		final boolean shouldUpdateUI;
		synchronized (this.lastFilteredRepos) {
			shouldUpdateUI = !filteredRepos.equals(this.lastFilteredRepos);
			this.lastFilteredRepos.clear();
			this.lastFilteredRepos.addAll(filteredRepos);
		}

		// publish filtered children map thread-safely
		synchronized (this.searchFilteredChildren) {
			this.searchFilteredChildren.clear();
			this.searchFilteredChildren.putAll(filteredMap);
		}

		// Update UI (minimal, avoid expandAll)
		org.eclipse.swt.widgets.Display.getDefault().asyncExec(() -> {
			if (this.modifiedObjTreeViewer.getControl().isDisposed()) {
				return;
			}
			this.repoOffsets.clear();

			this.modifiedObjTreeViewer.getControl().setRedraw(false);
			try {
				if (shouldUpdateUI) {
					this.modifiedObjTreeViewer.setInput(filteredRepos);
					this.modifiedObjTreeViewer.refresh();
					// Expand only first level (repositories) for responsiveness
					for (IRepositoryModifiedObjects repo : filteredRepos) {
						this.modifiedObjTreeViewer.expandToLevel(repo, 1);
					}
				} else {
					// children changed but repo set same; refresh visible nodes
					this.modifiedObjTreeViewer.refresh();
				}
			} finally {
				this.modifiedObjTreeViewer.getControl().setRedraw(true);
			}
		});

		// update lastQuery to reflect executed search
		this.lastQuery = query;
	}

	private void createColumns() {
		createTreeViewerColumn("Name", 300).setLabelProvider(new ColumnLabelProvider() { //$NON-NLS-1$
			@Override
			public String getText(Object element) {
				if (element instanceof IRepositoryModifiedObjects) {
					return "Repository: " + RepositoryUtil.getRepoNameFromUrl(((IRepositoryModifiedObjects) element).getRepositoryURL()); //$NON-NLS-1$
				} else if (element instanceof IOverwriteObject) {
					return ((IOverwriteObject) element).getName();
				}
				return ""; //$NON-NLS-1$
			}
		});

		createTreeViewerColumn("Package", 200).setLabelProvider(new ColumnLabelProvider() { //$NON-NLS-1$
			@Override
			public String getText(Object element) {
				if (element instanceof IOverwriteObject) {
					return ((IOverwriteObject) element).getPackageName();
				}
				return ""; //$NON-NLS-1$
			}
		});

		createTreeViewerColumn("Type", 100).setLabelProvider(new ColumnLabelProvider() { //$NON-NLS-1$
			@Override
			public String getText(Object element) {
				if (element instanceof IOverwriteObject) {
					return ((IOverwriteObject) element).getType();
				}
				return ""; //$NON-NLS-1$
			}
		});

		createTreeViewerColumn("Action", 150).setLabelProvider(new ColumnLabelProvider() { //$NON-NLS-1$
			@Override
			public String getText(Object element) {
				if (element instanceof IOverwriteObject) {
					String actionDescription = ((IOverwriteObject) element).getActionDescription();
					if (actionDescription == null || actionDescription.trim().isEmpty()) {
						return "Modify object locally"; //$NON-NLS-1$
					}
					return actionDescription;
				}
				return ""; //$NON-NLS-1$
			}
		});
	}

	private TreeViewerColumn createTreeViewerColumn(String title, int bound) {
		TreeViewerColumn viewerColumn = new TreeViewerColumn(this.modifiedObjTreeViewer, SWT.NONE);
		viewerColumn.getColumn().setText(title);
		this.treeColumnLayout.setColumnData(viewerColumn.getColumn(), new ColumnWeightData(20, bound, true));
		return viewerColumn;
	}

	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		if (visible) {
			this.searchText.setText(""); // trigger search reset //$NON-NLS-1$
			this.searchText.setMessage("Search objects..."); //$NON-NLS-1$
			this.repoOffsets.clear();
			this.searchFilteredChildren.clear();
			this.modifiedObjTreeViewer.refresh();
			if (this.repoToModifiedObjects.isEmpty()) {
				getContainer().showPage(getNextPage());
			}
		}
	}

	@Override
	public boolean canFlipToNextPage() {
		return getNextPage() != null;
	}

	@Override
	public IWizardPage getNextPage() {
		this.selectedObjectsForRepository = this.modifiedObjTreeViewer.getCheckedElements();
		return super.getNextPage();
	}

	@SuppressWarnings("unchecked")
	public Set<IRepositoryModifiedObjects> getSelectedObjects() {
		Set<IRepositoryModifiedObjects> checkedObjectsForRepository = new HashSet<>();
		Set<IRepositoryModifiedObjects> input = (Set<IRepositoryModifiedObjects>) this.modifiedObjTreeViewer.getInput();

		for (IRepositoryModifiedObjects modifiedObjectsForRepository : input) {
			List<IOverwriteObject> objects = new ArrayList<>();
			String repositoryURL = modifiedObjectsForRepository.getRepositoryURL();

			for (IOverwriteObject obj : modifiedObjectsForRepository.getModifiedObjects()) {
				if (Arrays.asList(this.selectedObjectsForRepository).contains(obj)) {
					objects.add(obj);
				}
			}
			if (!objects.isEmpty()) {
				checkedObjectsForRepository.add(new RepositoryModifiedObjects(repositoryURL, objects));
			}
		}
		return checkedObjectsForRepository;
	}

	private class ModifiedObjectTreeContentProvider implements ITreeContentProvider {
		private static final int BATCH_SIZE = 100;

		@Override
		public Object[] getElements(Object inputElement) {
			return ArrayContentProvider.getInstance().getElements(inputElement);
		}

		@Override
		public Object[] getChildren(Object parentElement) {
			if (parentElement instanceof IRepositoryModifiedObjects) {
				IRepositoryModifiedObjects repo = (IRepositoryModifiedObjects) parentElement;

				// If search is active, return filtered children
				synchronized (AbapGitWizardPageObjectsSelectionForPull.this.searchFilteredChildren) {
					if (!AbapGitWizardPageObjectsSelectionForPull.this.searchFilteredChildren.isEmpty()
							&& AbapGitWizardPageObjectsSelectionForPull.this.searchFilteredChildren.containsKey(repo)) {
						return AbapGitWizardPageObjectsSelectionForPull.this.searchFilteredChildren.get(repo).toArray();
					}
				}

				List<IOverwriteObject> all = repo.getModifiedObjects();
				int loaded = AbapGitWizardPageObjectsSelectionForPull.this.repoOffsets.getOrDefault(repo, 0);
				if (loaded == 0) {
					loaded = Math.min(BATCH_SIZE, all.size());
					AbapGitWizardPageObjectsSelectionForPull.this.repoOffsets.put(repo, loaded);
				}
				return all.subList(0, loaded).toArray();
			}
			return new Object[0];
		}

		@Override
		public boolean hasChildren(Object element) {
			if (element instanceof IRepositoryModifiedObjects) {
				synchronized (AbapGitWizardPageObjectsSelectionForPull.this.searchFilteredChildren) {
					if (!AbapGitWizardPageObjectsSelectionForPull.this.searchFilteredChildren.isEmpty()
							&& AbapGitWizardPageObjectsSelectionForPull.this.searchFilteredChildren.containsKey(element)) {
						return !AbapGitWizardPageObjectsSelectionForPull.this.searchFilteredChildren.get(element).isEmpty();
					}
				}
				return !((IRepositoryModifiedObjects) element).getModifiedObjects().isEmpty();
			}
			return false;
		}

		@Override
		public Object getParent(Object element) {
			return null;
		}
	}

	private void addListeners() {
		this.modifiedObjTreeViewer.addCheckStateListener(new ICheckStateListener() {
			@Override
			public void checkStateChanged(CheckStateChangedEvent event) {
				Object element = event.getElement();

				if (element instanceof IRepositoryModifiedObjects) {
					IRepositoryModifiedObjects repo = (IRepositoryModifiedObjects) element;
					boolean checked = event.getChecked();
					for (IOverwriteObject obj : repo.getModifiedObjects()) {
						if (!"1".equals(obj.getAction())) { //$NON-NLS-1$
							AbapGitWizardPageObjectsSelectionForPull.this.modifiedObjTreeViewer.setChecked(obj, checked);
						} else {
							AbapGitWizardPageObjectsSelectionForPull.this.modifiedObjTreeViewer.setChecked(obj, true);
						}
					}
				} else if (element instanceof IOverwriteObject) {
					IOverwriteObject child = (IOverwriteObject) element;
					IRepositoryModifiedObjects parent = findParent(child);

					if (parent != null) {
						List<IOverwriteObject> all = parent.getModifiedObjects();
						boolean allChecked = true;
						for (IOverwriteObject obj : all) {
							if (!AbapGitWizardPageObjectsSelectionForPull.this.modifiedObjTreeViewer.getChecked(obj)
									&& !"1".equals(obj.getAction())) { //$NON-NLS-1$
								allChecked = false;
								break;
							}
						}
						AbapGitWizardPageObjectsSelectionForPull.this.modifiedObjTreeViewer.setChecked(parent, allChecked);
					}

					if ("1".equals(child.getAction())) { //$NON-NLS-1$
						AbapGitWizardPageObjectsSelectionForPull.this.modifiedObjTreeViewer.setChecked(child, true);
					}
				}
				AbapGitWizardPageObjectsSelectionForPull.this.selectedObjectsForRepository = AbapGitWizardPageObjectsSelectionForPull.this.modifiedObjTreeViewer
						.getCheckedElements();
			}

			private IRepositoryModifiedObjects findParent(IOverwriteObject obj) {
				for (IRepositoryModifiedObjects repo : AbapGitWizardPageObjectsSelectionForPull.this.repoToModifiedObjects) {
					if (repo.getModifiedObjects().contains(obj)) {
						return repo;
					}
				}
				return null;
			}
		});

		this.modifiedObjTreeViewer.setCheckStateProvider(new ICheckStateProvider() {
			@Override
			public boolean isGrayed(Object element) {
				return false;
			}

			@Override
			public boolean isChecked(Object element) {
				if (element instanceof IOverwriteObject) {
					return "1".equals(((IOverwriteObject) element).getAction()); //$NON-NLS-1$
				}
				return false;
			}
		});

		var tree = this.modifiedObjTreeViewer.getTree();
		tree.addListener(SWT.MouseWheel, e -> tryLazyLoad());
		tree.addListener(SWT.Selection, e -> tryLazyLoad());
		tree.addListener(SWT.Resize, e -> tryLazyLoad());
		tree.addListener(SWT.MouseUp, e -> tryLazyLoad());
	}

	private void tryLazyLoad() {
		// disable lazy load while search is active
		synchronized (this.searchFilteredChildren) {
			if (!this.searchFilteredChildren.isEmpty()) {
				return;
			}
		}

		var tree = this.modifiedObjTreeViewer.getTree();
		ScrollBar vBar = tree.getVerticalBar();
		if (vBar == null || vBar.getMaximum() == 0) {
			return;
		}

		int scrollPosition = vBar.getSelection() + vBar.getThumb();
		int threshold = (int) (vBar.getMaximum() * 0.9);

		if (scrollPosition >= threshold) {
			Object[] expandedElements = this.modifiedObjTreeViewer.getExpandedElements();

			for (Object parent : expandedElements) {
				if (parent instanceof IRepositoryModifiedObjects) {
					IRepositoryModifiedObjects repo = (IRepositoryModifiedObjects) parent;
					loadNextBatch(repo);
				}
			}
		}
	}

	private void loadNextBatch(IRepositoryModifiedObjects repo) {
		List<IOverwriteObject> all = repo.getModifiedObjects();
		int currentOffset = this.repoOffsets.getOrDefault(repo, 0);

		if (currentOffset >= all.size()) {
			return;
		}

		int nextOffset = Math.min(currentOffset + ModifiedObjectTreeContentProvider.BATCH_SIZE, all.size());
		List<IOverwriteObject> nextBatch = all.subList(currentOffset, nextOffset);
		this.repoOffsets.put(repo, nextOffset);

		this.modifiedObjTreeViewer.getControl().setRedraw(false);
		try {
			for (IOverwriteObject obj : nextBatch) {
				this.modifiedObjTreeViewer.add(repo, obj);
				if ("1".equals(obj.getAction())) { //$NON-NLS-1$
					this.modifiedObjTreeViewer.setChecked(obj, true);
				}
			}
		} finally {
			this.modifiedObjTreeViewer.getControl().setRedraw(true);
		}
	}
}
