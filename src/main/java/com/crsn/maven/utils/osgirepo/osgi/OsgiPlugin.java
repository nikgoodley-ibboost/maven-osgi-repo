package com.crsn.maven.utils.osgirepo.osgi;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import org.eclipse.osgi.util.ManifestElement;
import org.osgi.framework.BundleException;
import org.osgi.framework.Version;

public class OsgiPlugin {
	
	private final File location;
	
	
	private final String pluginName;
	private final Version pluginVersion;

	public OsgiPlugin(File location) {
		if (location == null) {
			throw new NullPointerException("Null location.");
		}
		this.location = location;
		try {
			ZipFile zipFile=new ZipFile(location);
			
			InputStream content = zipFile.getInputStream(zipFile.getEntry("META-INF/MANIFEST.MF"));
			
			try {
				
				HashMap<String, String> headers = new HashMap<String, String>();
				ManifestElement.parseBundleManifest(content,headers);

				
				ManifestElement[] elements = ManifestElement.parseHeader("Bundle-SymbolicName", headers.get("Bundle-SymbolicName"));
				if (elements.length != 1) {
					throw new IllegalArgumentException("more than one symbolic name");
				}
				this.pluginName=elements[0].getValue();
				
				
				
				this.pluginVersion=new Version(headers.get("Bundle-Version"));
				
				
				
			} catch (IOException e) {
				throw new RuntimeException(e);
			} catch (BundleException e) {
				throw new RuntimeException(e);
			}
	
			
		} catch (ZipException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
	}

	public String getName() {
		return pluginName;
	}

	public Version getVersion() {
		return this.pluginVersion;
	}
	
	public File getLocation() {
		return location;
	}
	

}
