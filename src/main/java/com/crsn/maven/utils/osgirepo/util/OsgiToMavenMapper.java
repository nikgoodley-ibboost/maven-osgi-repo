package com.crsn.maven.utils.osgirepo.util;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.crsn.maven.utils.osgirepo.maven.MavenRepository;
import com.crsn.maven.utils.osgirepo.osgi.OsgiPlugin;

public class OsgiToMavenMapper {

	public static MavenRepository createRepository(List<OsgiPlugin> plugins) {
		MavenRepositoryBuilder builder = new MavenRepositoryBuilder();
		for (OsgiPlugin plugin : plugins) {
			String groupId = createGroupId(plugin);

			String artifactId = createArtifactName(plugin);

			String version = plugin.getVersion().toString();

			builder.addArtefact(groupId, version, artifactId,
					plugin.getLocation());

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
