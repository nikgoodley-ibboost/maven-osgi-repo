package com.crsn.maven.utils.osgirepo.maven;

import java.io.File;
import java.util.List;

public class MavenArtefact {

	private final MavenGroup group;
	private final String name;
	private final MavenVersion version;
	private final File content;

	public MavenArtefact(MavenGroup group, String name, MavenVersion version,
			File content) {
		if (group == null) {
			throw new NullPointerException("Null group.");
		}
		this.group = group;
		if (name == null) {
			throw new NullPointerException("Null name.");
		}
		this.name = name;

		if (version == null) {
			throw new NullPointerException("Null version");
		}
		this.version = version;

		if (this.content == null) {
			throw new NullPointerException("Null content.");
		}
		this.content = content;

	}

	public MavenGroup getGroup() {
		return group;
	}

	public String getName() {
		return name;
	}

	public MavenVersion getVersion() {
		return version;
	}

	public File getContent() {
		return content;
	}

}
