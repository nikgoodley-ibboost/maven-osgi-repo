package com.crsn.maven.utils.osgirepo.osgi;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import org.eclipse.osgi.util.ManifestElement;
import org.osgi.framework.BundleException;
import org.osgi.framework.Version;

public class JarOsgiPlugin implements OsgiPlugin {
	
	private final File location;
	
	
	private final String pluginName;
	private final Version pluginVersion;
	private final List<OsgiDependency> requiredBundles;

	public JarOsgiPlugin(File location) {
		if (location == null) {
			throw new NullPointerException("Null location.");
		}
		this.location = location;
		
		
		
		try {
			ZipFile zipFile=new ZipFile(location);
			
			InputStream content = zipFile.getInputStream(zipFile.getEntry("META-INF/MANIFEST.MF"));
			
			BundleParser parser=new BundleParser(content);
			
			this.pluginName=parser.getPluginName();
			this.pluginVersion=parser.getPluginVersion();
			this.requiredBundles=parser.getRequiredBundles();
			
			
		} catch (ZipException e) {
			throw new RuntimeException(String.format("Could not open '%s': %s",location.getAbsolutePath(),e.getMessage()),e);
		} catch (IOException e) {
			throw new RuntimeException(String.format("Could not open '%s': %s",location.getAbsolutePath(),e.getMessage()),e);
		}
		
	}

	/* (non-Javadoc)
	 * @see com.crsn.maven.utils.osgirepo.osgi.OsgiPlugin#getName()
	 */
	@Override
	public String getName() {
		return pluginName;
	}

	/* (non-Javadoc)
	 * @see com.crsn.maven.utils.osgirepo.osgi.OsgiPlugin#getVersion()
	 */
	@Override
	public Version getVersion() {
		return this.pluginVersion;
	}
	
	/* (non-Javadoc)
	 * @see com.crsn.maven.utils.osgirepo.osgi.OsgiPlugin#getLocation()
	 */
	@Override
	public File getLocation() {
		return location;
	}
	
	/* (non-Javadoc)
	 * @see com.crsn.maven.utils.osgirepo.osgi.OsgiPlugin#getRequiredBundles()
	 */
	@Override
	public List<OsgiDependency> getRequiredBundles() {
		return requiredBundles;
	}

}
