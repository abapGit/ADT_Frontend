package org.abapgit.adt.ui.internal.repositories.exceptions;

public class PackageRefNotFoundException extends RuntimeException {

	public PackageRefNotFoundException(String message) {
		super(message);
	}

	public PackageRefNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public PackageRefNotFoundException(Throwable cause) {
		super(cause);
	}
}
