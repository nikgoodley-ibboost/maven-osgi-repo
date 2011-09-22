package com.crsn.maven.utils.osgirepo.osgi;

import java.io.File;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;

public class OsgiRepository {

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
				System.out.printf("added plugin %s/%s%n", osgiPlugin.getName(), osgiPlugin.getVersion());
				plugins.add(osgiPlugin);

			} else if (file.isDirectory()) {
				DirectoryOsgiPlugin osgiPlugin = new DirectoryOsgiPlugin(file);
				System.out.printf("added plugin %s/%s%n", osgiPlugin.getName(), osgiPlugin.getVersion());
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
