package com.crsn.maven.utils.osgirepo.osgi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.filefilter.FileFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.plexus.util.IOUtil;
import org.eclipse.osgi.util.ManifestElement;
import org.osgi.framework.BundleException;
import org.osgi.framework.Version;

public class DirectoryOsgiPlugin implements OsgiPlugin {

	private final File location;

	private final String pluginName;
	private final Version pluginVersion;
	private final List<OsgiDependency> requiredBundles;

	private final List<String> contentFileNames;

	public DirectoryOsgiPlugin(File location) {
		if (location == null) {
			throw new NullPointerException("Null location.");
		}
		this.location = location;

		if (!location.isDirectory()) {
			throw new RuntimeException(String.format("Location '%s' is not a directory.", location.getAbsolutePath()));
		}

		try {

			InputStream content = new FileInputStream(new File(location, "META-INF/MANIFEST.MF"));

			BundleParser parser = new BundleParser(content);

			this.pluginName = parser.getPluginName();
			this.pluginVersion = parser.getPluginVersion();
			this.requiredBundles = parser.getRequiredBundles();
			this.contentFileNames = parser.getClassPathEntries();

		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.crsn.maven.utils.osgirepo.osgi.OsgiPlugin#getName()
	 */
	@Override
	public String getName() {
		return pluginName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.crsn.maven.utils.osgirepo.osgi.OsgiPlugin#getVersion()
	 */
	@Override
	public Version getVersion() {
		return this.pluginVersion;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.crsn.maven.utils.osgirepo.osgi.OsgiPlugin#getLocation()
	 */
	@Override
	public File getLocation() {
		try {
			File tempFile = File.createTempFile("repacked", "jar");
			tempFile.delete();
			tempFile.deleteOnExit();
			JarOutputStream jarStream = new JarOutputStream(new FileOutputStream(tempFile));

			for (String contentFileName : this.contentFileNames) {
				File contentFile = new File(this.location, contentFileName);
				if (contentFile.isDirectory()) {
					copyDirectoryContentToJar(contentFile, jarStream);
				} else {
					copyJarContentToJar(contentFile, jarStream);
				}
			}
			
			jarStream.close();

			return tempFile;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private void copyJarContentToJar(File contentFile, JarOutputStream jarStream) {
		try {
			JarFile jarFile = new JarFile(contentFile);
			for (Enumeration<JarEntry> entries = jarFile.entries(); entries.hasMoreElements();) {
				JarEntry entry = entries.nextElement();
				InputStream inputStream = jarFile.getInputStream(entry);
				JarEntry copy=new JarEntry(entry.getName());
				jarStream.putNextEntry(copy);
				IOUtil.copy(inputStream, jarStream);
				inputStream.close();
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	private void copyDirectoryContentToJar(File parentFile, JarOutputStream jarStream) throws IOException {
		Iterator<File> iterateFiles = FileUtils.iterateFiles(parentFile, TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE);
		while (iterateFiles.hasNext()) {
			File nextFile=iterateFiles.next();
			
			if (nextFile.isFile()) {
				String name=stripParentName(nextFile,parentFile);
				JarEntry entry=new JarEntry(name);
				jarStream.putNextEntry(entry);
				FileInputStream input = new FileInputStream(nextFile);
				IOUtil.copy(input, jarStream);
				input.close();
			}			
		}

	}
	
	private static String stripParentName(File file, File parent) {
		File parentAbs = parent.getAbsoluteFile();
		File fileAbs = file.getAbsoluteFile();
		
		
		List<String> elements=new LinkedList<String>();
		while (!fileAbs.equals(parentAbs)) {
			elements.add(fileAbs.getName());
			fileAbs=fileAbs.getParentFile();
		}
		
		Collections.reverse(elements);
		
		
		String joined = StringUtils.join(elements,'/');
		return joined;
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.crsn.maven.utils.osgirepo.osgi.OsgiPlugin#getRequiredBundles()
	 */
	@Override
	public List<OsgiDependency> getRequiredBundles() {
		return requiredBundles;
	}

}
