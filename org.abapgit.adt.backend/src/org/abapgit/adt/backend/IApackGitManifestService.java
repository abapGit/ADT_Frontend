package org.abapgit.adt.backend;

import org.eclipse.core.runtime.IProgressMonitor;

public interface IApackGitManifestService {

	IApackManifest getManifest(String url, String branch, String user, String password, IProgressMonitor monitor);

}
