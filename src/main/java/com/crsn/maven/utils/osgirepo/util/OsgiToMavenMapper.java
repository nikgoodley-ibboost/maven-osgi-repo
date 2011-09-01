package com.crsn.maven.utils.osgirepo.util;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.osgi.framework.Version;

import com.crsn.maven.utils.osgirepo.maven.MavenRepository;
import com.crsn.maven.utils.osgirepo.maven.MavenVersion;
import com.crsn.maven.utils.osgirepo.maven.builder.MavenArtefactBuilder;
import com.crsn.maven.utils.osgirepo.maven.builder.MavenRepositoryBuilder;
import com.crsn.maven.utils.osgirepo.osgi.OsgiPlugin;
import com.crsn.maven.utils.osgirepo.osgi.OsgiRepository;

public class OsgiToMavenMapper {

	public static MavenRepository createRepository(OsgiRepository repository) {
		MavenRepositoryBuilder builder = new MavenRepositoryBuilder();
		for (OsgiPlugin plugin : repository.getPlugins()) {
			String groupId = createGroupId(plugin);

			String artifactId = createArtifactName(plugin);

			Version version = plugin.getVersion();

			MavenArtefactBuilder artefactBuilder = builder.addArtefact();
			artefactBuilder.setGroup(groupId);
			artefactBuilder.setArtefactId(artifactId);
			artefactBuilder.setVersion(new MavenVersion(version.getMajor(),
					version.getMinor(), version.getMicro()));
			artefactBuilder.setContent(plugin.getLocation());
			artefactBuilder.build();

		}

		return builder.build();

	}

	static String createGroupId(OsgiPlugin plugin) {
		List<String> parts = splitPluginName(plugin);

		String groupId = StringUtils.join(parts.subList(0, parts.size() - 1),
				'.');
		return groupId;
	}

	static String createArtifactName(OsgiPlugin plugin) {
		List<String> groupParts = splitPluginName(plugin);

		return groupParts.get(groupParts.size() - 1);
	}

	private static List<String> splitPluginName(OsgiPlugin plugin) {
		String pluginName = plugin.getName();

		List<String> parts = Arrays.asList(pluginName.split("\\."));

		return parts;
	}

}
