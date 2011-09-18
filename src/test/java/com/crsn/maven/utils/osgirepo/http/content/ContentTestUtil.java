package com.crsn.maven.utils.osgirepo.http.content;

import java.io.File;

import org.junit.Ignore;

import com.crsn.maven.utils.osgirepo.maven.MavenRepository;
import com.crsn.maven.utils.osgirepo.maven.MavenVersion;
import com.crsn.maven.utils.osgirepo.maven.builder.MavenArtifactBuilder;
import com.crsn.maven.utils.osgirepo.maven.builder.MavenDependencyBuilder;
import com.crsn.maven.utils.osgirepo.maven.builder.MavenRepositoryBuilder;

@Ignore
public class ContentTestUtil {

	static MavenRepository createMockMavenRepository() {
		MavenRepositoryBuilder builder = new MavenRepositoryBuilder();
		MavenArtifactBuilder artefactBuilder = builder.addArtefact();
		artefactBuilder.setArtifactId("boo");
		artefactBuilder.setGroup("com.crsn");
		artefactBuilder.setVersion(new MavenVersion(1, 0));
		artefactBuilder.setContent(new File("."));
		MavenDependencyBuilder dependencyBuilder = artefactBuilder
				.addDependency();
		dependencyBuilder.setArtefactId("dependency");
		dependencyBuilder.setGroupId("com.crsn");
		dependencyBuilder.setVersionRange(new MavenVersion(1, 0), true, null,
				false);
		dependencyBuilder.build();

		MavenDependencyBuilder secondDependencyBuilder = artefactBuilder
				.addDependency();
		secondDependencyBuilder.setArtefactId("dependency");
		secondDependencyBuilder.setGroupId("com.crsn");
		secondDependencyBuilder.setVersionRange(null, true, null, false);
		secondDependencyBuilder.build();

		artefactBuilder.build();

		MavenRepository repository = builder.build();
		return repository;
	}

}
