package com.crsn.maven.utils.osgirepo;

import java.io.File;
import java.util.List;

import com.sampullara.cli.Args;
import com.sampullara.cli.Argument;

public class Main {

	public static void main(String[] args) {
		Main main=new Main();
		try {
			parseArguments(args,main);
		} catch (IllegalArgumentException e) {
			System.err.println(e.getMessage());
			Args.usage(Main.class);
		}

	}

	static Main parseArguments(String[] args, Main arguments) {
	
		
		List<String> parse = Args.parse(arguments, args);
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
