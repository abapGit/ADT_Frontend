package org.abapgit.adt.ui;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

public final class AbapGitUIPlugin extends AbstractUIPlugin {

	private static AbapGitUIPlugin plugin;

	public static final String PLUGIN_ID = "org.abapgit.adt.ui"; //$NON-NLS-1$

	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		super.stop(context);
		plugin = null;
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static AbapGitUIPlugin getDefault() {
		return plugin;
	}

}
