package com.crsn.maven.utils.osgirepo.maven;

public class MavenVersionRange {

	private final MavenVersion fromVersion;
	private final boolean includingForm;
	private final MavenVersion toVersion;
	private final boolean includingTo;

	public MavenVersionRange(MavenVersion fromVersion, boolean includingForm,
			MavenVersion toVersion, boolean includingTo) {
		this.fromVersion = fromVersion;
		this.includingForm = includingForm;
		this.toVersion = toVersion;
		this.includingTo = includingTo;

	}

	public MavenVersion getFromVersion() {
		return fromVersion;
	}

	public MavenVersion getToVersion() {
		return toVersion;
	}

	public boolean isIncludingForm() {
		return includingForm;
	}

	public boolean isIncludingTo() {
		return includingTo;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}

}
