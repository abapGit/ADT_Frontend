package org.abapgit.adt.backend.internal;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.abapgit.adt.backend.IObject;
import org.abapgit.adt.backend.IObjects;

public class AbapObjects implements IObjects {

	private final List<IObject> objects = new LinkedList<>();

	public void addList(List<IObject> objects) {
		this.objects.addAll(objects);
	}

	@Override
	public List<IObject> getObjects() {
		return Collections.unmodifiableList(this.objects);
	}

	public void add(IObject object) {
		this.objects.add(object);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Objects [objects="); //$NON-NLS-1$
		builder.append(this.objects);
		builder.append("]"); //$NON-NLS-1$
		return builder.toString();
	}

}
