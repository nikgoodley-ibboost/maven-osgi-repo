package com.crsn.maven.utils.osgirepo.osgi;

import static org.junit.Assert.*;

import org.junit.Test;
import org.osgi.framework.Version;

public class BundleParserTest {

	private BundleParser parser=new BundleParser(this.getClass().getResourceAsStream("/complex-MANIFEST.MF"));
	
	@Test
	public void canParserBundleName() {
		
		assertEquals("org.eclipse.egit.core",parser.getPluginName());
		
	}
	
	@Test
	public void canParseBundleVersion() {
		assertEquals(new Version(1, 0, 0, "201106090707-r"),parser.getPluginVersion());
	}
	
	
	@Test
	public void canParseDependencies() {
		
		assertEquals(5,parser.getRequiredBundles().size());
		
	}

}
