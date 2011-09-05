package com.crsn.maven.utils.osgirepo.maven.builder;

import java.io.File;
import java.util.LinkedList;
import java.util.List;


import com.crsn.maven.utils.osgirepo.maven.MavenArtefact;
import com.crsn.maven.utils.osgirepo.maven.MavenDependency;
import com.crsn.maven.utils.osgirepo.maven.MavenGroup;
import com.crsn.maven.utils.osgirepo.maven.MavenRepository;
import com.crsn.maven.utils.osgirepo.maven.MavenVersion;
import com.crsn.maven.utils.osgirepo.maven.MavenVersionRange;

public class MavenRepositoryBuilder {

	private final List<MavenArtefact> artefacts = new LinkedList<MavenArtefact>();

	public MavenRepositoryBuilder() {

	}

	public MavenArtefactBuilder addArtefact() {
		return new MavenArtefactBuilderInternal();
	}

	public MavenRepository build() {
		return new MavenRepository(artefacts);
	}

	public class MavenArtefactBuilderInternal implements MavenArtefactBuilder {

		private MavenGroup group;
		private String artefactId;
		private MavenVersion version;
		private List<MavenDependency> dependencies = new LinkedList<MavenDependency>();
		private File content;

		public MavenArtefactBuilderInternal() {

		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.crsn.maven.utils.osgirepo.maven.builder.MavenAer#setGroup(java
		 * .lang.String)
		 */
		@Override
		public void setGroup(String groupId) {
			this.group = new MavenGroup(groupId);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.crsn.maven.utils.osgirepo.maven.builder.MavenAer#setArtefactId
		 * (java.lang.String)
		 */
		@Override
		public void setArtefactId(String artefactId) {
			this.artefactId = artefactId;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.crsn.maven.utils.osgirepo.maven.builder.MavenAer#setVersion(com
		 * .crsn.maven.utils.osgirepo.maven.MavenVersion)
		 */
		@Override
		public void setVersion(MavenVersion version) {
			this.version = version;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.crsn.maven.utils.osgirepo.maven.builder.MavenAer#addDependency()
		 */
		@Override
		public MavenDependencyBuilder addDependency() {
			return new InternalDependencyBuilder();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.crsn.maven.utils.osgirepo.maven.builder.MavenAer#setContent(java
		 * .io.File)
		 */
		@Override
		public void setContent(File content) {
			this.content = content;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.crsn.maven.utils.osgirepo.maven.builder.MavenAer#build()
		 */
		@Override
		public void build() {
			artefacts.add(new MavenArtefact(group, artefactId, version,
					dependencies, content));
		}

		private class InternalDependencyBuilder implements
				MavenDependencyBuilder {

			private String groupId;
			private String artefactId;
			private MavenVersionRange versionRange;

			@Override
			public void setGroupId(String groupId) {
				this.groupId = groupId;

			}

			@Override
			public void setArtefactId(String artefactId) {
				this.artefactId = artefactId;

			}

			@Override
			public void setVersionRange(MavenVersion from,
					boolean includingFrom, MavenVersion to, boolean includingTo) {
				this.versionRange = new MavenVersionRange(from, includingFrom,
						to, includingTo);

			}

			@Override
			public void build() {
				dependencies.add(new MavenDependency(new MavenGroup(groupId), artefactId,
						versionRange));

			}

		}

	}

}
