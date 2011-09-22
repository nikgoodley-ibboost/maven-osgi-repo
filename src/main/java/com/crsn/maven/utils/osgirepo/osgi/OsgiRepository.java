package com.crsn.maven.utils.osgirepo.osgi;

import java.io.File;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;

import com.google.code.mjl.Log;
import com.google.code.mjl.LogFactory;

public class OsgiRepository {

	private static Log log = LogFactory.getLog();

	private List<OsgiPlugin> plugins;

	private OsgiRepository(List<OsgiPlugin> plugins) {
		this.plugins = plugins;
	}

	public static OsgiRepository createRepository(File directory) {

		LinkedList<OsgiPlugin> plugins = new LinkedList<OsgiPlugin>();

		if (directory == null) {
			throw new NullPointerException("Null directory.");
		}

		File[] listFiles = directory.listFiles();

		for (File file : listFiles) {
			if (file.isFile() && file.getName().toLowerCase().endsWith("jar")) {
				JarOsgiPlugin osgiPlugin = new JarOsgiPlugin(file);
				log.info("added plugin %s/%s", osgiPlugin.getName(), osgiPlugin.getVersion());
				plugins.add(osgiPlugin);

			} else if (file.isDirectory()) {
				DirectoryOsgiPlugin osgiPlugin = new DirectoryOsgiPlugin(file);
				log.info("added plugin %s/%s", osgiPlugin.getName(), osgiPlugin.getVersion());
				plugins.add(osgiPlugin);
			}
		}

		return new OsgiRepository(plugins);

	}

	public static OsgiRepository createRepository(List<? extends OsgiPlugin> plugins) {
		return new OsgiRepository(new LinkedList<OsgiPlugin>(plugins));
	}

	public List<OsgiPlugin> getPlugins() {
		return Collections.unmodifiableList(plugins);
	}

	public OsgiPlugin resolveDependency(OsgiDependency dependency) {

		if (dependency == null) {
			throw new NullPointerException("Null dependency.");
		}

		TreeSet<OsgiPlugin> resolved = new TreeSet<OsgiPlugin>(new Comparator<OsgiPlugin>() {
			@Override
			public int compare(OsgiPlugin o1, OsgiPlugin o2) {
				return o1.getVersion().compareTo(o2.getVersion());
			}
		});

		for (OsgiPlugin osgiPlugin : this.plugins) {
			if (dependency.isResolvedBy(osgiPlugin)) {
				resolved.add(osgiPlugin);
			}
		}

		if (resolved.isEmpty()) {
			return null;
		} else {
			return resolved.last();
		}

	}

}
