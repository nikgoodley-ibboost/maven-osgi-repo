package com.crsn.maven.utils.osgirepo.maven;

public class MavenGroup {

	private final String groupId;

	public MavenGroup(String groupId) {
		if (groupId == null) {
			throw new NullPointerException("Null group id.");
		}
		this.groupId = groupId;
	}

	public String getGroupId() {
		return groupId;
	}

}
