package com.crsn.maven.utils.osgirepo.util;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

import com.crsn.maven.utils.osgirepo.osgi.OsgiPlugin;

public class OsgiToMavenMapperTest {

	@Test
	public void canMapArtifactName() {
		OsgiPlugin osgiPlugin = new OsgiPlugin(
				TestUtil.getFileOfResource("org.eclipse.xtext.xtend2.lib_2.0.1.v201108020636.jar"));
		String artifactName = OsgiToMavenMapper.createArtifactName(osgiPlugin);
		assertEquals("lib", artifactName);

	}

	@Test
	public void canMapArtifactGroup() {
		OsgiPlugin osgiPlugin = new OsgiPlugin(
				TestUtil.getFileOfResource("org.eclipse.xtext.xtend2.lib_2.0.1.v201108020636.jar"));

		String groupId = OsgiToMavenMapper.createGroupId(osgiPlugin);
		assertEquals("org.eclipse.xtext.xtend2", groupId);

	}

}
