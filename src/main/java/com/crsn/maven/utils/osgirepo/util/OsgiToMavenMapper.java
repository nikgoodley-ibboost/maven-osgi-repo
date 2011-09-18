package com.crsn.maven.utils.osgirepo.util;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.osgi.framework.Version;

import com.crsn.maven.utils.osgirepo.maven.MavenRepository;
import com.crsn.maven.utils.osgirepo.maven.MavenVersion;
import com.crsn.maven.utils.osgirepo.maven.builder.MavenArtifactBuilder;
import com.crsn.maven.utils.osgirepo.maven.builder.MavenDependencyBuilder;
import com.crsn.maven.utils.osgirepo.maven.builder.MavenRepositoryBuilder;
import com.crsn.maven.utils.osgirepo.osgi.OsgiDependency;
import com.crsn.maven.utils.osgirepo.osgi.OsgiPlugin;
import com.crsn.maven.utils.osgirepo.osgi.OsgiRepository;
import com.crsn.maven.utils.osgirepo.osgi.VersionRange;

public class OsgiToMavenMapper {

	public static MavenRepository createRepository(OsgiRepository repository) {
		MavenRepositoryBuilder builder = new MavenRepositoryBuilder();
		for (OsgiPlugin plugin : repository.getPlugins()) {
			String groupId = createGroupId(plugin.getName());

			String artifactId = createArtifactName(plugin.getName());

			Version version = plugin.getVersion();

			MavenArtifactBuilder artefactBuilder = builder.addArtefact();
			artefactBuilder.setGroup(groupId);
			artefactBuilder.setArtifactId(artifactId);
			artefactBuilder.setVersion(createMavenVersion(version));
			artefactBuilder.setContent(plugin.getLocation());

			for (OsgiDependency osgiDependency : plugin.getRequiredBundles()) {
				MavenDependencyBuilder dependencyBuilder = artefactBuilder
						.addDependency();
				dependencyBuilder.setArtefactId(createArtifactName(osgiDependency.getName()));
				dependencyBuilder.setGroupId(createGroupId(osgiDependency
						.getName()));

				VersionRange versionRange = osgiDependency.getVersionRange();

				dependencyBuilder.setVersionRange(
						createMavenVersion(versionRange.getFrom()),
						versionRange.isIncludingFrom(),
						createMavenVersion(versionRange.getTo()),
						versionRange.isIncludingTo());
				dependencyBuilder.build();
			}

			artefactBuilder.build();

		}

		return builder.build();

	}

	private static MavenVersion createMavenVersion(Version version) {
		if (version == null) {
			return null;
		}
		return new MavenVersion(version.getMajor(), version.getMinor(),
				version.getMicro());
	}

	static String createGroupId(String pluginName) {
		// String pluginName = plugin.getName();

		List<String> parts1 = Arrays.asList(pluginName.split("\\."));
		List<String> parts = parts1;

		String groupId = StringUtils.join(parts.subList(0, parts.size() - 1),
				'.');
		return groupId;
	}

	static String createArtifactName(String pluginName) {
//		String pluginName = plugin.getName();

		List<String> parts = Arrays.asList(pluginName.split("\\."));
		List<String> groupParts = parts;

		return groupParts.get(groupParts.size() - 1);
	}

}
