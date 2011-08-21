package com.crsn.maven.utils.osgirepo.osgi;

import static org.junit.Assert.*;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;

import org.junit.Test;
import org.osgi.framework.Version;

public class OsgiPluginTest {
	
	private final OsgiPlugin plugin;
	
	public OsgiPluginTest() throws UnsupportedEncodingException {
		URL resource = this.getClass().getResource("/org.eclipse.xtext.xtend2.lib_2.0.1.v201108020636.jar");
		String encodedFileName = resource.getFile();
		String decodedFileName = URLDecoder.decode(encodedFileName, "UTF-8");
		this.plugin=new OsgiPlugin(new File(decodedFileName));
	}

	@Test
	public void canGetName() {
		assertEquals("org.eclipse.xtext.xtend2.lib",plugin.getName());
	}
	

	@Test
	public void canGetVersion() {
		assertEquals(new Version(2, 0, 1,"v201108020636"),plugin.getVersion());
	}


}
