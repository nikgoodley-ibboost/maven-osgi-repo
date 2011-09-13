package com.crsn.maven.utils.osgirepo;

import java.io.File;

import com.crsn.maven.utils.osgirepo.http.HttpServer;
import com.crsn.maven.utils.osgirepo.maven.MavenRepository;
import com.crsn.maven.utils.osgirepo.osgi.OsgiRepository;
import com.crsn.maven.utils.osgirepo.util.MavenRepositoryToHttpContentsMapper;
import com.crsn.maven.utils.osgirepo.util.OsgiToMavenMapper;

public class MavenOsgiRepository {
	private final HttpServer server;

	public MavenOsgiRepository(File repository, int port) {
		server = new HttpServer(port);
		OsgiRepository osgiRepository=new OsgiRepository(repository);
		MavenRepository mavenRepository = OsgiToMavenMapper.createRepository(osgiRepository);
		MavenRepositoryToHttpContentsMapper.registerArtefacts(mavenRepository, server);
		server.start();
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
