package com.crsn.maven.utils.osgirepo.util;

import java.util.List;

import com.crsn.maven.utils.osgirepo.http.Content;
import com.crsn.maven.utils.osgirepo.http.HttpServer;
import com.crsn.maven.utils.osgirepo.http.content.JarFileContent;
import com.crsn.maven.utils.osgirepo.http.content.DigestContent;
import com.crsn.maven.utils.osgirepo.http.content.PomContent;
import com.crsn.maven.utils.osgirepo.maven.MavenArtefact;
import com.crsn.maven.utils.osgirepo.maven.MavenRepository;

public class MavenRepositoryToHttpContentsMapper {

	public static void registerArtefacts(MavenRepository repository,
			HttpServer server) {
		List<MavenArtefact> artefacts = repository.getArtefacts();
		for (MavenArtefact mavenArtefact : artefacts) {

			String directory = String.format("/%s/%s/%s", mavenArtefact
					.getGroup().toString().replaceAll("\\.", "/"),
					mavenArtefact.getName(), mavenArtefact.getVersion()
							.toString());

			PomContent pomContent = new PomContent(mavenArtefact);
			registerContentAndItsDigests(pomContent, mavenArtefact, server,
					directory, "pom");

			JarFileContent jarContent = new JarFileContent(
					mavenArtefact.getContent());
			registerContentAndItsDigests(jarContent, mavenArtefact, server,
					directory, "jar");

		}
	}

	private static void registerContentAndItsDigests(Content content,
			MavenArtefact mavenArtefact, HttpServer server, String directory,
			String extension) {
		String contentUrl = String.format("%s/%s-%s.%s", directory,
				mavenArtefact.getName(), mavenArtefact.getVersion(), extension);
		server.registerContent(contentUrl, content);

		registerMacForContent(content, server, contentUrl, "MD5");

		registerMacForContent(content, server, contentUrl, "SHA1");
	}

	private static String registerMacForContent(Content content,
			HttpServer server, String originalUrl, String macType) {

		DigestContent digestContent = new DigestContent(macType, content);
		String digestUrl = String.format("%s.%s", originalUrl,
				macType.toLowerCase());
		server.registerContent(digestUrl, digestContent);

		return digestUrl;
	}

}
