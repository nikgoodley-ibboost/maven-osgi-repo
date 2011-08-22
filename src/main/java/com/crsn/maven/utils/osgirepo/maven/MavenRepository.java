package com.crsn.maven.utils.osgirepo.maven;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class MavenRepository {

	private final List<MavenArtefact> artefacts;

	public MavenRepository(List<MavenArtefact> artefacts) {
		if (artefacts == null) {
			throw new NullPointerException("Null artefacts.");
		}
		this.artefacts = new LinkedList<MavenArtefact>(artefacts);

	}

	public List<MavenArtefact> getArtefacts() {
		return Collections.unmodifiableList(artefacts);
	}

}
