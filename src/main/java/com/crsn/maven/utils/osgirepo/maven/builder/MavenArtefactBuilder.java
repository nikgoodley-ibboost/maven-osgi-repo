package com.crsn.maven.utils.osgirepo.maven.builder;

import java.io.File;

import com.crsn.maven.utils.osgirepo.maven.MavenArtefact;
import com.crsn.maven.utils.osgirepo.maven.MavenVersion;

public interface MavenArtefactBuilder {

	void setGroup(String groupId);

	void setArtefactId(String artefactId);

	void setVersion(MavenVersion version);

	MavenDependencyBuilder addDependency();

	void setContent(File content);

	void build();

}