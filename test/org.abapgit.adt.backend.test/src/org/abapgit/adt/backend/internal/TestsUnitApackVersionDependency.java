package org.abapgit.adt.backend.internal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.abapgit.adt.backend.IApackManifest.EApackVersionDependencyClassifier;
import org.abapgit.adt.backend.IApackManifest.IApackVersionDependency;
import org.junit.Test;

public class TestsUnitApackVersionDependency {

	@Test
	public void standardRange() {
		IApackVersionDependency versionDependency = new ApackVersionDependency("(0.2,1.0]");
		assertEquals("0.2", versionDependency.getMinimum());
		assertEquals(EApackVersionDependencyClassifier.inclusive, versionDependency.getMinimumClassifier());
		assertEquals(0, versionDependency.getMinimumMajor());
		assertEquals(2, versionDependency.getMinimumMinor());
		assertEquals(0, versionDependency.getMinimumPatch());
		assertEquals("1.0", versionDependency.getMaximum());
		assertEquals(EApackVersionDependencyClassifier.exclusive, versionDependency.getMaximumClassifier());
		assertEquals(1, versionDependency.getMaximumMajor());
		assertEquals(0, versionDependency.getMaximumMinor());
		assertEquals(0, versionDependency.getMaximumPatch());
		assertTrue(versionDependency.hasRange());
		assertTrue(versionDependency.isValid());
	}

	@Test
	public void singleCompleteVersion() {
		IApackVersionDependency versionDependency = new ApackVersionDependency("0.23.42");
		assertEquals("0.23.42", versionDependency.getMinimum());
		assertEquals(EApackVersionDependencyClassifier.inclusive, versionDependency.getMinimumClassifier());
		assertEquals(0, versionDependency.getMinimumMajor());
		assertEquals(23, versionDependency.getMinimumMinor());
		assertEquals(42, versionDependency.getMinimumPatch());
		assertEquals("0.23.42", versionDependency.getMaximum());
		assertEquals(EApackVersionDependencyClassifier.inclusive, versionDependency.getMaximumClassifier());
		assertEquals(0, versionDependency.getMaximumMajor());
		assertEquals(23, versionDependency.getMaximumMinor());
		assertEquals(42, versionDependency.getMaximumPatch());
		assertFalse(versionDependency.hasRange());
		assertTrue(versionDependency.isValid());
	}

	@Test
	public void singleVersionMajorOnly() {
		IApackVersionDependency versionDependency = new ApackVersionDependency("5");
		assertEquals("5", versionDependency.getMinimum());
		assertEquals(EApackVersionDependencyClassifier.inclusive, versionDependency.getMinimumClassifier());
		assertEquals(5, versionDependency.getMinimumMajor());
		assertEquals(0, versionDependency.getMinimumMinor());
		assertEquals(0, versionDependency.getMinimumPatch());
		assertEquals("5", versionDependency.getMaximum());
		assertEquals(EApackVersionDependencyClassifier.inclusive, versionDependency.getMaximumClassifier());
		assertEquals(5, versionDependency.getMaximumMajor());
		assertEquals(0, versionDependency.getMaximumMinor());
		assertEquals(0, versionDependency.getMaximumPatch());
		assertFalse(versionDependency.hasRange());
		assertTrue(versionDependency.isValid());
	}
	
	@Test
	public void singleVersionMajorMinor() {
		IApackVersionDependency versionDependency = new ApackVersionDependency("0.2");
		assertEquals("0.2", versionDependency.getMinimum());
		assertEquals(EApackVersionDependencyClassifier.inclusive, versionDependency.getMinimumClassifier());
		assertEquals(0, versionDependency.getMinimumMajor());
		assertEquals(2, versionDependency.getMinimumMinor());
		assertEquals(0, versionDependency.getMinimumPatch());
		assertEquals("0.2", versionDependency.getMaximum());
		assertEquals(EApackVersionDependencyClassifier.inclusive, versionDependency.getMaximumClassifier());
		assertEquals(0, versionDependency.getMaximumMajor());
		assertEquals(2, versionDependency.getMaximumMinor());
		assertEquals(0, versionDependency.getMaximumPatch());
		assertFalse(versionDependency.hasRange());
		assertTrue(versionDependency.isValid());
	}
	
	@Test
	public void singleCompleteVersionBuildNumber() {
		// We don't allow dependency declarations using build IDs!
		IApackVersionDependency versionDependency = new ApackVersionDependency("0.34.453-45");
		assertFalse(versionDependency.isValid());
	}
	
	@Test
	public void rangeDoubleInclusive() {
		IApackVersionDependency versionDependency = new ApackVersionDependency("(0.3,1.11.4)");
		assertEquals("0.3", versionDependency.getMinimum());
		assertEquals(EApackVersionDependencyClassifier.inclusive, versionDependency.getMinimumClassifier());
		assertEquals(0, versionDependency.getMinimumMajor());
		assertEquals(3, versionDependency.getMinimumMinor());
		assertEquals(0, versionDependency.getMinimumPatch());
		assertEquals("1.11.4", versionDependency.getMaximum());
		assertEquals(EApackVersionDependencyClassifier.inclusive, versionDependency.getMaximumClassifier());
		assertEquals(1, versionDependency.getMaximumMajor());
		assertEquals(11, versionDependency.getMaximumMinor());
		assertEquals(4, versionDependency.getMaximumPatch());
		assertTrue(versionDependency.hasRange());
		assertTrue(versionDependency.isValid());
	}
	
	@Test
	public void rangeExclusiveInclusive() {
		IApackVersionDependency versionDependency = new ApackVersionDependency("[0.1,1.0)");
		assertEquals("0.1", versionDependency.getMinimum());
		assertEquals(EApackVersionDependencyClassifier.exclusive, versionDependency.getMinimumClassifier());
		assertEquals(0, versionDependency.getMinimumMajor());
		assertEquals(1, versionDependency.getMinimumMinor());
		assertEquals(0, versionDependency.getMinimumPatch());
		assertEquals("1.0", versionDependency.getMaximum());
		assertEquals(EApackVersionDependencyClassifier.inclusive, versionDependency.getMaximumClassifier());
		assertEquals(1, versionDependency.getMaximumMajor());
		assertEquals(0, versionDependency.getMaximumMinor());
		assertEquals(0, versionDependency.getMaximumPatch());
		assertTrue(versionDependency.hasRange());
		assertTrue(versionDependency.isValid());
	}
	
	@Test
	public void rangeExclusiveExclusive() {
		IApackVersionDependency versionDependency = new ApackVersionDependency("[0.34.45,1.1.0]");
		assertEquals("0.34.45", versionDependency.getMinimum());
		assertEquals(EApackVersionDependencyClassifier.exclusive, versionDependency.getMinimumClassifier());
		assertEquals(0, versionDependency.getMinimumMajor());
		assertEquals(34, versionDependency.getMinimumMinor());
		assertEquals(45, versionDependency.getMinimumPatch());
		assertEquals("1.1.0", versionDependency.getMaximum());
		assertEquals(EApackVersionDependencyClassifier.exclusive, versionDependency.getMaximumClassifier());
		assertEquals(1, versionDependency.getMaximumMajor());
		assertEquals(1, versionDependency.getMaximumMinor());
		assertEquals(0, versionDependency.getMaximumPatch());
		assertTrue(versionDependency.hasRange());
		assertTrue(versionDependency.isValid());
	}
	
	@Test
	public void versionCompatibleInclusiveExclusive() {
		IApackVersionDependency versionDependency = new ApackVersionDependency("(0.1.42,1.0.33]");
		assertFalse(versionDependency.isVersionCompatible("0.1.11"));
		assertTrue(versionDependency.isVersionCompatible("0.1.42-42"));
		assertTrue(versionDependency.isVersionCompatible("0.999.999"));
		assertTrue(versionDependency.isVersionCompatible("1.0"));
		assertFalse(versionDependency.isVersionCompatible("1.0.33"));
		assertFalse(versionDependency.isVersionCompatible("1.0.34"));
	}
	
	@Test
	public void versionCompatibleInclusiveInclusive() {
		IApackVersionDependency versionDependency = new ApackVersionDependency("(0.1.42,1.0.33)");
		assertFalse(versionDependency.isVersionCompatible("0.1.11"));
		assertTrue(versionDependency.isVersionCompatible("0.1.42-42"));
		assertTrue(versionDependency.isVersionCompatible("0.999.999"));
		assertTrue(versionDependency.isVersionCompatible("1.0"));
		assertTrue(versionDependency.isVersionCompatible("1.0.33"));
		assertFalse(versionDependency.isVersionCompatible("1.0.34"));
	}
	
	@Test
	public void versionCompatibleExclusiveInclusive() {
		IApackVersionDependency versionDependency = new ApackVersionDependency("[0.1.42,1.0.33)");
		assertFalse(versionDependency.isVersionCompatible("0.1.11"));
		assertFalse(versionDependency.isVersionCompatible("0.1.42-42"));
		assertTrue(versionDependency.isVersionCompatible("0.1.43-42"));
		assertTrue(versionDependency.isVersionCompatible("0.999.999"));
		assertTrue(versionDependency.isVersionCompatible("1.0"));
		assertTrue(versionDependency.isVersionCompatible("1.0.33"));
		assertFalse(versionDependency.isVersionCompatible("1.0.34"));
	}
	
	@Test
	public void versionCompatibleExclusiveExclusive() {
		IApackVersionDependency versionDependency = new ApackVersionDependency("[0.1.42,1.0.33]");
		assertFalse(versionDependency.isVersionCompatible("0.1.11"));
		assertFalse(versionDependency.isVersionCompatible("0.1.42-42"));
		assertTrue(versionDependency.isVersionCompatible("0.1.43-42"));
		assertTrue(versionDependency.isVersionCompatible("0.999.999"));
		assertTrue(versionDependency.isVersionCompatible("1.0"));
		assertFalse(versionDependency.isVersionCompatible("1.0.33"));
		assertFalse(versionDependency.isVersionCompatible("1.0.34"));
	}
	

}
