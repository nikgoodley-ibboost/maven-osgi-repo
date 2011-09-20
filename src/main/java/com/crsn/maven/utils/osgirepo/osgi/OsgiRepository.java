package com.crsn.maven.utils.osgirepo.osgi;

import java.io.File;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOCase;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.FalseFileFilter;
import org.apache.commons.io.filefilter.OrFileFilter;
import org.apache.commons.io.filefilter.SuffixFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;

public class OsgiRepository {

	private List<OsgiPlugin> plugins = new LinkedList<OsgiPlugin>();

	public OsgiRepository(File directory) {
		if (directory == null) {
			throw new NullPointerException("Null directory.");
		}

		File[] listFiles = directory.listFiles();

		for (File file : listFiles) {
			if (file.isFile() && file.getName().toLowerCase().endsWith("jar")) {
				JarOsgiPlugin osgiPlugin = new JarOsgiPlugin(file);
				System.out.printf("added plugin %s/%s%n", osgiPlugin.getName(), osgiPlugin.getVersion());
				plugins.add(osgiPlugin);

			} else if (file.isDirectory()) {
				DirectoryOsgiPlugin osgiPlugin = new DirectoryOsgiPlugin(file);
				System.out.printf("added plugin %s/%s%n", osgiPlugin.getName(), osgiPlugin.getVersion());
				plugins.add(osgiPlugin);
			}
		}

	}

	public List<OsgiPlugin> getPlugins() {
		return Collections.unmodifiableList(plugins);
	}

}
