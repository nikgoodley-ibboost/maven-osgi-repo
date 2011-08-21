package com.crsn.maven.utils.osgirepo.maven;

import java.util.List;

public class MavenArtefact {
	
	private final MavenGroup group;
	private final String name;
	private final MavenVersion version;

	public MavenArtefact(MavenGroup group, String name, MavenVersion version) {
		this.group = group;
		this.name = name;
		this.version = version;
		
	}

}
