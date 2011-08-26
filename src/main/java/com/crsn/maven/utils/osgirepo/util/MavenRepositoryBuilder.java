package com.crsn.maven.utils.osgirepo.util;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import org.osgi.framework.Version;

import com.crsn.maven.utils.osgirepo.maven.MavenArtefact;
import com.crsn.maven.utils.osgirepo.maven.MavenGroup;
import com.crsn.maven.utils.osgirepo.maven.MavenRepository;
import com.crsn.maven.utils.osgirepo.maven.MavenVersion;

class MavenRepositoryBuilder {
	
	private final List<MavenArtefact> artefacts=new LinkedList<MavenArtefact>();
	
	public MavenRepositoryBuilder() {
		
	}
	
	
	public void addArtefact(String groupId, MavenVersion version , String artefactId, File content) {
		MavenArtefact artefact=new MavenArtefact(new MavenGroup(groupId), artefactId, version, content);
		artefacts.add(artefact);
	}
	
	public MavenRepository build() {
		return new MavenRepository(artefacts);
	}

}
