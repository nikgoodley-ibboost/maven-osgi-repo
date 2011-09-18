package com.crsn.maven.utils.osgirepo.maven;

import java.util.List;

public class MavenArtifactVersions {
	
	private final String groupId;
	private final String artifactId;
	private final List<MavenVersion> versions;

	public MavenArtifactVersions(String groupId, String artifactId, List<MavenVersion> versions) {
		this.groupId = groupId;
		this.artifactId = artifactId;
		this.versions = versions;
		
	}

	public String getGroupId() {
		return groupId;
	}

	public String getArtifactId() {
		return artifactId;
	}

	public List<MavenVersion> getVersions() {
		return versions;
	}
	
	
	

}
