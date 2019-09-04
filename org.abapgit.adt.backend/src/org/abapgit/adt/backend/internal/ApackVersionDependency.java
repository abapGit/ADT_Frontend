package org.abapgit.adt.backend.internal;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.abapgit.adt.backend.IApackManifest.EApackVersionDependencyClassifier;
import org.abapgit.adt.backend.IApackManifest.IApackVersionDependency;

public class ApackVersionDependency implements IApackVersionDependency {

	public static final String SINGLE_VERSION = "([0-9]{1,9})\\.?([0-9]{1,9})?\\.?([0-9]{1,9})?"; //$NON-NLS-1$
	public static final String VERSION_RANGE = "(\\(|\\[)(" + SINGLE_VERSION + "),(" + SINGLE_VERSION + ")(\\]|\\))"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	public static final Pattern PATTERN_SINGLE_VERSION = Pattern.compile(SINGLE_VERSION);
	public static final Pattern PATTERN_VERSION_RANGE = Pattern.compile(VERSION_RANGE);

	private String minimum;
	private int minimumMajor;
	private int minimumMinor;
	private int minimumPatch;
	private int maximumMajor;
	private int maximumMinor;
	private int maximumPatch;
	private boolean range;
	private EApackVersionDependencyClassifier minimumClassifier;
	private String maximum;
	private EApackVersionDependencyClassifier maximumClassifier;
	private boolean valid;

	public ApackVersionDependency(String rawVersion) {
		this.valid = false;
		if (rawVersion == null) {
			return;
		}
		if (rawVersion.matches(VERSION_RANGE)) {
			Matcher rangeMatcher = PATTERN_VERSION_RANGE.matcher(rawVersion);
			rangeMatcher.find();
			if ("(".equals(rangeMatcher.group(1))) { //$NON-NLS-1$
				this.minimumClassifier = EApackVersionDependencyClassifier.inclusive;
			} else {
				this.minimumClassifier = EApackVersionDependencyClassifier.exclusive;
			}
			if (")".equals(rangeMatcher.group(10))) { //$NON-NLS-1$
				this.maximumClassifier = EApackVersionDependencyClassifier.inclusive;
			} else {
				this.maximumClassifier = EApackVersionDependencyClassifier.exclusive;
			}
			this.minimum = rangeMatcher.group(2);
			this.minimumMajor = Integer.parseInt(rangeMatcher.group(3));
			this.minimumMinor = rangeMatcher.group(4) == null ? 0 : Integer.parseInt(rangeMatcher.group(4));
			this.minimumPatch = rangeMatcher.group(5) == null ? 0 : Integer.parseInt(rangeMatcher.group(5));
			this.maximum = rangeMatcher.group(6);
			this.maximumMajor = Integer.parseInt(rangeMatcher.group(7));
			this.maximumMinor = rangeMatcher.group(8) == null ? 0 : Integer.parseInt(rangeMatcher.group(8));
			this.maximumPatch = rangeMatcher.group(9) == null ? 0 : Integer.parseInt(rangeMatcher.group(9));
			this.range = true;
			this.valid = true;
		} else if (rawVersion.matches(SINGLE_VERSION)) {
			Matcher versionMatcher = PATTERN_SINGLE_VERSION.matcher(rawVersion);
			versionMatcher.find();
			this.minimumClassifier = EApackVersionDependencyClassifier.inclusive;
			this.minimum = versionMatcher.group(0);
			this.minimumMajor = Integer.parseInt(versionMatcher.group(1));
			this.minimumMinor = versionMatcher.group(2) == null ? 0 : Integer.parseInt(versionMatcher.group(2));
			this.minimumPatch = versionMatcher.group(3) == null ? 0 : Integer.parseInt(versionMatcher.group(3));
			this.maximumClassifier = EApackVersionDependencyClassifier.inclusive;
			this.maximum = this.minimum;
			this.maximumMajor = this.minimumMajor;
			this.maximumMinor = this.minimumMinor;
			this.maximumPatch = this.minimumPatch;
			this.range = false;
			this.valid = true;
		}
	}

	@Override
	public String getMinimum() {
		return this.minimum;
	}

	@Override
	public EApackVersionDependencyClassifier getMinimumClassifier() {
		return this.minimumClassifier;
	}

	@Override
	public String getMaximum() {
		return this.maximum;
	}

	@Override
	public EApackVersionDependencyClassifier getMaximumClassifier() {
		return this.maximumClassifier;
	}

	@Override
	public boolean isVersionCompatible(String version) {
		String versionWithSuffixRegex = "(" + SINGLE_VERSION + ").*"; //$NON-NLS-1$ //$NON-NLS-2$
		if (version.matches(versionWithSuffixRegex)) {
			Matcher versionSuffixMatcher = Pattern.compile(versionWithSuffixRegex).matcher(version);
			versionSuffixMatcher.find();
			IApackVersionDependency otherVersion = new ApackVersionDependency(versionSuffixMatcher.group(1));
			if (getMinimumMajor() > otherVersion.getMinimumMajor()) {
				return false;
			}
			if (getMinimumMajor() == otherVersion.getMinimumMajor()) {
				if (getMinimumMinor() > otherVersion.getMinimumMinor()) {
					return false;
				}
				if (getMinimumMinor() == otherVersion.getMinimumMinor()) {
					if (getMinimumPatch() > otherVersion.getMinimumPatch()) {
						return false;
					}
					if (getMinimumPatch() == otherVersion.getMinimumPatch()
							&& getMinimumClassifier() == EApackVersionDependencyClassifier.exclusive) {
						return false;
					}
				}
			}

			if (getMaximumMajor() < otherVersion.getMaximumMajor()) {
				return false;
			}
			if (getMaximumMajor() > otherVersion.getMaximumMajor()) {
				return true;
			}
			if (getMaximumMinor() < otherVersion.getMaximumMinor()) {
				return false;
			}
			if (getMaximumMinor() > otherVersion.getMaximumMinor()) {
				return true;
			}
			if (getMaximumPatch() < otherVersion.getMaximumPatch()) {
				return false;
			}
			if (getMaximumPatch() > otherVersion.getMaximumPatch()) {
				return true;
			}
			if (getMaximumClassifier() == EApackVersionDependencyClassifier.inclusive) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("APACK Version Dependency [minimum="); //$NON-NLS-1$
		builder.append(this.getMinimum());
		builder.append(", minimumClassifier="); //$NON-NLS-1$
		builder.append(this.getMinimumClassifier());
		builder.append(", maximum="); //$NON-NLS-1$
		builder.append(this.getMaximum());
		builder.append(", maximumClassifier="); //$NON-NLS-1$
		builder.append(this.getMaximumClassifier());
		builder.append("]"); //$NON-NLS-1$
		return builder.toString();
	}

	@Override
	public int hashCode() { // NOPMD
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.getMinimum() == null) ? 0 : this.getMinimum().hashCode());
		result = prime * result + ((this.getMinimumClassifier() == null) ? 0 : this.getMinimumClassifier().hashCode());
		result = prime * result + ((this.getMaximum() == null) ? 0 : this.getMaximum().hashCode());
		result = prime * result + ((this.getMaximumClassifier() == null) ? 0 : this.getMaximumClassifier().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) { // NOPMD
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		ApackVersionDependency other = (ApackVersionDependency) obj;
		if (this.getMinimum() == null) {
			if (other.getMinimum() != null) {
				return false;
			}
		} else if (!this.getMinimum().equals(other.getMinimum())) {
			return false;
		}
		if (this.getMinimumClassifier() != other.getMinimumClassifier()) {
			return false;
		}
		if (this.getMaximum() == null) {
			if (other.getMaximum() != null) {
				return false;
			}
		} else if (!this.getMaximum().equals(other.getMaximum())) {
			return false;
		}
		return this.getMaximumClassifier() == other.getMaximumClassifier();
	}

	public void setMinimum(String minimum) {
		this.minimum = minimum;
	}

	public void setMaximum(String maximum) {
		this.maximum = maximum;
	}

	public void setMinimumClassifier(EApackVersionDependencyClassifier minimumClassifier) {
		this.minimumClassifier = minimumClassifier;
	}

	public void setMaximumClassifier(EApackVersionDependencyClassifier maximumClassifier) {
		this.maximumClassifier = maximumClassifier;
	}

	@Override
	public int getMinimumMajor() {
		return this.minimumMajor;
	}

	@Override
	public int getMinimumMinor() {
		return this.minimumMinor;
	}

	@Override
	public int getMinimumPatch() {
		return this.minimumPatch;
	}

	@Override
	public int getMaximumMajor() {
		return this.maximumMajor;
	}

	@Override
	public int getMaximumMinor() {
		return this.maximumMinor;
	}

	@Override
	public int getMaximumPatch() {
		return this.maximumPatch;
	}

	@Override
	public boolean hasRange() {
		return this.range;
	}

	@Override
	public boolean isValid() {
		return this.valid;
	}

}
