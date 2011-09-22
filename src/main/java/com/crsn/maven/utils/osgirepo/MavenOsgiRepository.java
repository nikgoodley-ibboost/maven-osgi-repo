package com.crsn.maven.utils.osgirepo;

import java.io.File;

import com.crsn.maven.utils.osgirepo.http.HttpServer;
import com.crsn.maven.utils.osgirepo.maven.MavenRepository;
import com.crsn.maven.utils.osgirepo.osgi.OsgiRepository;
import com.crsn.maven.utils.osgirepo.util.MavenRepositoryToHttpContentsMapper;
import com.crsn.maven.utils.osgirepo.util.OsgiToMavenMapper;

public class MavenOsgiRepository {
	private final HttpServer server;

	public MavenOsgiRepository(File repositoryDirectory, int port) {
		server = new HttpServer(port);
		OsgiRepository osgiRepository = OsgiRepository.createRepository(repositoryDirectory);
		MavenRepository mavenRepository = OsgiToMavenMapper.createRepository(osgiRepository);
		MavenRepositoryToHttpContentsMapper.registerArtefacts(mavenRepository, server);
	}

	public void start() {
		server.start();
	}

	public void stop() {
		server.stop();
	}

	public String getServerUrl() {
		return server.getServerUrl();
	}

}
