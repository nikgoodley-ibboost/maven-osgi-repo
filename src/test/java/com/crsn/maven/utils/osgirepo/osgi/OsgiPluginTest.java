package com.crsn.maven.utils.osgirepo.osgi;

import static org.junit.Assert.*;

import java.io.File;
import java.io.UnsupportedEncodingException;

import org.junit.Test;
import org.osgi.framework.Version;

import com.crsn.maven.utils.osgirepo.util.TestUtil;

public class OsgiPluginTest {

	private final OsgiPlugin plugin = new JarOsgiPlugin(
			TestUtil.getFileOfResource("mockrepo/org.eclipse.xtext.xtend2.lib_2.0.1.v201108020636.jar"));;

	@Test
	public void canGetName() {
		assertEquals("org.eclipse.xtext.xtend2.lib", plugin.getName());
	}

	@Test
	public void canGetVersion() {
		assertEquals(new Version(2, 0, 1, "v201108020636"), plugin.getVersion());
	}

}
