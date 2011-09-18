package com.crsn.maven.utils.osgirepo.maven;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class MavenRepository {

	private final List<MavenArtifact> artefacts;

	public MavenRepository(List<MavenArtifact> artefacts) {
		if (artefacts == null) {
			throw new NullPointerException("Null artefacts.");
		}
		this.artefacts = new LinkedList<MavenArtifact>(artefacts);

	}

	public List<MavenArtifact> getArtefacts() {
		return Collections.unmodifiableList(artefacts);
	}

}
