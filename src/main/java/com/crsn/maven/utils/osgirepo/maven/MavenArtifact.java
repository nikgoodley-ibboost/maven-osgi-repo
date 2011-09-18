package com.crsn.maven.utils.osgirepo.maven;

import java.io.File;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class MavenArtifact {

	private final String group;
	private final String id;
	private final MavenVersion version;
	private final File content;
	private final List<MavenDependency> dependencies;

	public MavenArtifact(String group, String id, MavenVersion version,
			List<MavenDependency> dependencies, File content) {
		
		if (group == null) {
			throw new NullPointerException("Null group.");
		}
		this.group = group;
		if (id == null) {
			throw new NullPointerException("Null name.");
		}
		this.id = id;

		if (version == null) {
			throw new NullPointerException("Null version");
		}
		this.version = version;
		
		
		if (dependencies == null) {
			throw new NullPointerException("Null dependencies");
		}
		this.dependencies = new LinkedList<MavenDependency>(dependencies);

		if (content == null) {
			throw new NullPointerException("Null content.");
		}
		this.content = content;

	}

	public String getGroup() {
		return group;
	}

	public String getId() {
		return id;
	}

	public MavenVersion getVersion() {
		return version;
	}

	public File getContent() {
		return content;
	}

	 
	public List<MavenDependency> getDependencies() {
		return Collections.unmodifiableList(dependencies);
	}

}
