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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((groupId == null) ? 0 : groupId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MavenGroup other = (MavenGroup) obj;
		if (groupId == null) {
			if (other.groupId != null)
				return false;
		} else if (!groupId.equals(other.groupId))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return groupId;
	}

}
