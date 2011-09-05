package com.crsn.maven.utils.osgirepo.osgi;

import static org.junit.Assert.assertFalse;

import org.junit.Test;

import com.crsn.maven.utils.osgirepo.util.TestUtil;

public class OsgiRepositoryTest {

	
	@Test
	public void canLoadRepository() {
		OsgiRepository repo=new OsgiRepository(TestUtil.getFileOfResource("mockrepo"));
		assertFalse(repo.getPlugins().isEmpty());
		OsgiPlugin plugin = repo.getPlugins().get(0);
		assertFalse(plugin.getRequiredBundles().isEmpty());
	}

}
