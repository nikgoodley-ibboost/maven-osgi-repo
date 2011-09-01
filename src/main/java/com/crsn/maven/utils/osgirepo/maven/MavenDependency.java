package com.crsn.maven.utils.osgirepo.maven;

public class MavenDependency {
	
	private final MavenGroup group;
	private final String artefactId;
	private final MavenVersionRange versionRange;

	public MavenDependency(MavenGroup group, String artefactId, MavenVersionRange versionRange) {
		if (group == null) {
			throw new NullPointerException("Null group.");
		}
		this.group = group;
		if (artefactId == null) {
			throw new NullPointerException("Null artefact ID.");
		}
		this.artefactId = artefactId;
		
		if (versionRange == null) {
			throw new NullPointerException("Null version range.");
		}
		this.versionRange = versionRange;
		
	}

	
	public String getArtefactId() {
		return artefactId;
	}
	
	public MavenGroup getGroup() {
		return group;
	}
	
	public MavenVersionRange getVersionRange() {
		return versionRange;
	}
	
	
}
