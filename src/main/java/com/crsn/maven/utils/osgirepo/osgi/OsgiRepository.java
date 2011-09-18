package com.crsn.maven.utils.osgirepo.osgi;

import java.io.File;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class OsgiRepository {
	
	private List<JarOsgiPlugin> plugins=new LinkedList<JarOsgiPlugin>();
	
	public OsgiRepository(File directory) {
		if (directory == null) {
			throw new NullPointerException("Null directory.");
		}
		
		Collection<File> listFiles = FileUtils.listFiles(directory, new String[]{"jar"}, false);
		for (File file : listFiles) {
			
			JarOsgiPlugin osgiPlugin = new JarOsgiPlugin(file);
			System.out.printf("added plugin %s/%s%n",osgiPlugin.getName(), osgiPlugin.getVersion());
			plugins.add(osgiPlugin);
		}
		
	}
	
	public List<JarOsgiPlugin> getPlugins() {
		return Collections.unmodifiableList(plugins);
	}

}
