package com.crsn.maven.utils.osgirepo.util;

import static org.junit.Assert.*;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.osgi.framework.Version;

import com.crsn.maven.utils.osgirepo.maven.MavenArtifact;
import com.crsn.maven.utils.osgirepo.maven.MavenDependency;
import com.crsn.maven.utils.osgirepo.maven.MavenRepository;
import com.crsn.maven.utils.osgirepo.maven.MavenVersion;
import com.crsn.maven.utils.osgirepo.osgi.JarOsgiPlugin;
import com.crsn.maven.utils.osgirepo.osgi.OsgiDependency;
import com.crsn.maven.utils.osgirepo.osgi.OsgiPlugin;
import com.crsn.maven.utils.osgirepo.osgi.OsgiRepository;
import com.crsn.maven.utils.osgirepo.osgi.VersionRange;

public class OsgiToMavenMapperTest {

	private final OsgiPlugin osgiPlugin = new JarOsgiPlugin(
			TestUtil.getFileOfResource("mockrepo/org.eclipse.xtext.xtend2.lib_2.0.1.v201108020636.jar"));

	@Test
	public void canMapArtifactName() {

		String artifactName = OsgiToMavenMapper.createArtifactName(osgiPlugin.getName());
		assertEquals("lib", artifactName);

	}

	@Test
	public void canMapArtifactGroup() {
		String groupId = OsgiToMavenMapper.createGroupId(osgiPlugin.getName());
		assertEquals("org.eclipse.xtext.xtend2", groupId);
	}

	@Test
	public void canCreateMavenRepository() {

		MockOsgiPlugin firstPlugin = new MockOsgiPlugin("a.a.a", new Version("2.0.1"),
				Collections.singletonList(new OsgiDependency("a.b.c", VersionRange.parseVersionRange("2.0"))));

		MockOsgiPlugin secondPlugin = new MockOsgiPlugin("a.b.c", new Version("3.0.0"),
				Collections.<OsgiDependency> emptyList());

		List<MockOsgiPlugin> plugins = Arrays.asList(firstPlugin, secondPlugin);
		OsgiRepository osgiRepository = OsgiRepository.createRepository(plugins);
		MavenRepository mavenRepository = OsgiToMavenMapper.createRepository(osgiRepository);

		assertNotNull(mavenRepository);
		List<MavenArtifact> artefacts = mavenRepository.getArtifacts();
		assertFalse(artefacts.isEmpty());
		MavenArtifact artefact = artefacts.get(0);
		assertEquals(new MavenVersion(2, 0, 1), artefact.getVersion());
		List<MavenDependency> dependencies = artefact.getDependencies();
		assertFalse(dependencies.isEmpty());
		MavenDependency firstDependency = dependencies.get(0);
		assertEquals("c", firstDependency.getArtefactId());
		assertEquals("a.b", firstDependency.getGroup().toString());
		assertEquals("3.0.0", firstDependency.getVersionRange().toString());

	}
}
