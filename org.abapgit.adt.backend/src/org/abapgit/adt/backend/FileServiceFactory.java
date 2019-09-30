package org.abapgit.adt.backend;

import org.abapgit.adt.backend.internal.FileService;

public class FileServiceFactory {
	private static IFileService instance;

	//singleton
	private FileServiceFactory() {
	}

	public static IFileService createFileService() {
		if (instance == null) {
			instance = new FileService();
		}
		return instance;
	}
}
