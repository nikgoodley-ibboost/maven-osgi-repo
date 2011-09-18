package com.crsn.maven.utils.osgirepo.util;

import static org.junit.Assert.*;

import java.io.File;
import java.util.List;

import org.junit.Test;

import com.crsn.maven.utils.osgirepo.maven.MavenArtifact;
import com.crsn.maven.utils.osgirepo.maven.MavenDependency;
import com.crsn.maven.utils.osgirepo.maven.MavenRepository;
import com.crsn.maven.utils.osgirepo.maven.MavenVersion;
import com.crsn.maven.utils.osgirepo.osgi.OsgiPlugin;
import com.crsn.maven.utils.osgirepo.osgi.OsgiRepository;

public class OsgiToMavenMapperTest {

	private final OsgiPlugin osgiPlugin = new OsgiPlugin(
			TestUtil.getFileOfResource("mockrepo/org.eclipse.xtext.xtend2.lib_2.0.1.v201108020636.jar"));

	private final OsgiRepository repository = new OsgiRepository(
			TestUtil.getFileOfResource("mockrepo/"));

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
		MavenRepository mavenRepository = OsgiToMavenMapper
				.createRepository(repository);
		assertNotNull(mavenRepository);
		List<MavenArtifact> artefacts = mavenRepository.getArtifacts();
		assertFalse(artefacts.isEmpty());
		MavenArtifact artefact = artefacts.get(0);
		assertEquals(new MavenVersion(2,0,1), artefact.getVersion());
		List<MavenDependency> dependencies = artefact.getDependencies();
		assertFalse(dependencies.isEmpty());
		MavenDependency firstDependency = dependencies.get(0);
		assertEquals("lib",firstDependency.getArtefactId());
		assertEquals("org.eclipse.xtext.xbase",firstDependency.getGroup().toString());
	}
}
