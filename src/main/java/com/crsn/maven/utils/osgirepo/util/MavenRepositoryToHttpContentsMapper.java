package com.crsn.maven.utils.osgirepo.util;

import java.util.List;

import com.crsn.maven.utils.osgirepo.http.HttpServer;
import com.crsn.maven.utils.osgirepo.http.content.PomContent;
import com.crsn.maven.utils.osgirepo.maven.MavenArtefact;
import com.crsn.maven.utils.osgirepo.maven.MavenRepository;

public class MavenRepositoryToHttpContentsMapper {
	
	
	public static void registerArtefacts(MavenRepository repository, HttpServer server) {
		List<MavenArtefact> artefacts = repository.getArtefacts();
		for (MavenArtefact mavenArtefact : artefacts) {
			String directory=String.format("/%s/%s/%s",mavenArtefact.getGroup().toString().replaceAll("\\.", "/"),mavenArtefact.getName(),mavenArtefact.getVersion().toString());
			PomContent pom=new PomContent(mavenArtefact);
			String pomUrl=String.format("%s/%s-%s.pom",directory,mavenArtefact.getName(),mavenArtefact.getVersion());
			server.registerContent(pomUrl, pom);
			System.out.printf("registered %s%n",pomUrl);
			
		}
	}

}
