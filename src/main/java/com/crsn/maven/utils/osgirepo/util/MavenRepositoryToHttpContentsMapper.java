package com.crsn.maven.utils.osgirepo.util;

import java.util.List;

import com.crsn.maven.utils.osgirepo.http.Content;
import com.crsn.maven.utils.osgirepo.http.HttpServer;
import com.crsn.maven.utils.osgirepo.http.content.ArtifactMetadataContent;
import com.crsn.maven.utils.osgirepo.http.content.DigestContent;
import com.crsn.maven.utils.osgirepo.http.content.JarFileContent;
import com.crsn.maven.utils.osgirepo.http.content.PomContent;
import com.crsn.maven.utils.osgirepo.maven.MavenArtifact;
import com.crsn.maven.utils.osgirepo.maven.MavenArtifactVersions;
import com.crsn.maven.utils.osgirepo.maven.MavenRepository;
import com.crsn.maven.utils.osgirepo.maven.MavenVersion;

public class MavenRepositoryToHttpContentsMapper {

	public static void registerArtefacts(MavenRepository repository, HttpServer server) {
		List<MavenArtifact> artefacts = repository.getArtifacts();
		for (MavenArtifact mavenArtefact : artefacts) {

			String directory = String.format("/%s/%s/%s", mavenArtefact.getGroupId().replaceAll("\\.", "/"),
					mavenArtefact.getArtifactId(), mavenArtefact.getVersion().toString());

			PomContent pomContent = new PomContent(mavenArtefact);
			registerContentAndItsDigests(pomContent, mavenArtefact, server, directory, "pom");

			JarFileContent jarContent = new JarFileContent(mavenArtefact.getContent());
			registerContentAndItsDigests(jarContent, mavenArtefact, server, directory, "jar");

		}

		for (MavenArtifactVersions mavenArtifactVersions : repository.getArtifactVersions()) {
			
			
			String contentFile = String.format("/%s/%s/maven-metadata.xml", mavenArtifactVersions.getGroupId().replaceAll("\\.", "/"),
					mavenArtifactVersions.getArtifactId());
			
			MavenVersion lastVersion = mavenArtifactVersions.getVersions().last();
			ArtifactMetadataContent metadataContent=new ArtifactMetadataContent(mavenArtifactVersions, lastVersion, lastVersion);
			
			server.registerContent(contentFile, metadataContent);
			registerMacForContent(metadataContent, server, contentFile, "MD5");
			registerMacForContent(metadataContent, server, contentFile, "SHA1");
			
		}
	}

	private static void registerContentAndItsDigests(Content content, MavenArtifact mavenArtefact, HttpServer server,
			String directory, String extension) {
		String contentUrl = String.format("%s/%s-%s.%s", directory, mavenArtefact.getArtifactId(),
				mavenArtefact.getVersion(), extension);
		server.registerContent(contentUrl, content);

		registerMacForContent(content, server, contentUrl, "MD5");

		registerMacForContent(content, server, contentUrl, "SHA1");
	}

	private static String registerMacForContent(Content content, HttpServer server, String originalUrl, String macType) {

		DigestContent digestContent = new DigestContent(macType, content);
		String digestUrl = String.format("%s.%s", originalUrl, macType.toLowerCase());
		server.registerContent(digestUrl, digestContent);

		return digestUrl;
	}

}
