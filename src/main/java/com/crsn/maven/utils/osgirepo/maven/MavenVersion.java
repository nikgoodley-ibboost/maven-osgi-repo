package com.crsn.maven.utils.osgirepo.maven;

public class MavenVersion {
	
	private final String versionName;

	public MavenVersion(String versionName) {
		this.versionName = versionName;
	}
	
	public String getVersionName() {
		return versionName;
	}

	@Override
	public String toString() {
		return versionName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((versionName == null) ? 0 : versionName.hashCode());
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
		MavenVersion other = (MavenVersion) obj;
		if (versionName == null) {
			if (other.versionName != null)
				return false;
		} else if (!versionName.equals(other.versionName))
			return false;
		return true;
	}
	
	

}
