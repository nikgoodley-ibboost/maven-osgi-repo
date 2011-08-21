package com.crsn.maven.utils.osgirepo.osgi;

import java.io.File;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class OsgiRepository {
	
	private List<OsgiPlugin> plugins=new LinkedList<OsgiPlugin>();
	
	public OsgiRepository(File directory) {
		if (directory == null) {
			throw new NullPointerException("Null directory.");
		}
		
		Collection<File> listFiles = FileUtils.listFiles(directory, new String[]{"jar"}, false);
		for (File file : listFiles) {
			
			OsgiPlugin osgiPlugin = new OsgiPlugin(file);
			System.out.printf("added plugin %s/%s%n",osgiPlugin.getName(), osgiPlugin.getVersion());
			plugins.add(osgiPlugin);
		}
		
	}

}
