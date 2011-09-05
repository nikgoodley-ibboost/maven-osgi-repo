package com.crsn.maven.utils.osgirepo;

import java.io.File;
import java.util.List;

import com.crsn.maven.utils.osgirepo.http.HttpServer;
import com.crsn.maven.utils.osgirepo.maven.MavenRepository;
import com.crsn.maven.utils.osgirepo.osgi.OsgiRepository;
import com.crsn.maven.utils.osgirepo.util.MavenRepositoryToHttpContentsMapper;
import com.crsn.maven.utils.osgirepo.util.OsgiToMavenMapper;
import com.sampullara.cli.Args;
import com.sampullara.cli.Argument;

public class Main {

	public static void main(String[] args) {
		Main main=new Main();
		try {
			parseArguments(args,main);
		} catch (IllegalArgumentException e) {
			System.err.println(e.getMessage());
			Args.usage(main);
			return;
		}
		
		main.execute();

	}
	
	
	private void execute() {
		HttpServer server=new HttpServer(port);
		OsgiRepository osgiRepository=new OsgiRepository(pluginRepository);
		MavenRepository mavenRepository = OsgiToMavenMapper.createRepository(osgiRepository);
		MavenRepositoryToHttpContentsMapper.registerArtefacts(mavenRepository, server);
		server.start();
		try {
			Thread.sleep(Long.MAX_VALUE);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		} finally {
			server.stop();
		}
	}

	static Main parseArguments(String[] args, Main arguments) {	
		Args.parse(arguments, args);
		return arguments;
	}

	@Argument(value = "plugin_repository", alias = "r", description = "Directory containing OSGi plugins", required = true)
	private File pluginRepository;

	@Argument(value = "port", alias = "p", description = "Local port for HTTP service", required = true)
	private Integer port;

	public File getPluginRepository() {
		return pluginRepository;
	}

	public int getPort() {
		return port;
	}

}
