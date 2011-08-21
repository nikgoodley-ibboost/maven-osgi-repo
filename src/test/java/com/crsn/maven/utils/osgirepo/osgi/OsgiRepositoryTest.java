package com.crsn.maven.utils.osgirepo.osgi;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Ignore;
import org.junit.Test;

public class OsgiRepositoryTest {

	@Ignore
	@Test
	public void canLoadRepository() {
		OsgiRepository repo=new OsgiRepository(new File("/Applications/eclipse-indigo/plugins/"));
		
	}

}
