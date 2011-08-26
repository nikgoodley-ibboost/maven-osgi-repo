package com.crsn.maven.utils.osgirepo.maven;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;


@Ignore
public class MavenVersionRangeTest {

	
	@Test
	public void supportsSingleVersion() {
		assertEquals("1.0",new MavenVersionRange(new MavenVersion(1,0), false, new MavenVersion(1,0), false));
	}
	
	@Test
	public void supportsLowerBoundExcludingVersion() {
		assertEquals("(1.0,)",new MavenVersionRange(new MavenVersion(1,0), false, new MavenVersion(1,0), false));
	}

}
