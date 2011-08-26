package com.crsn.maven.utils.osgirepo.util;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;

import org.custommonkey.xmlunit.Diff;
import org.custommonkey.xmlunit.XMLAssert;
import org.custommonkey.xmlunit.XMLUnit;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import com.crsn.maven.utils.osgirepo.maven.MavenArtefact;
import com.crsn.maven.utils.osgirepo.maven.MavenGroup;
import com.crsn.maven.utils.osgirepo.maven.MavenVersion;
import static org.custommonkey.xmlunit.XMLAssert.*;

public class PomFileContentTest {

	@Before
	public void setUpXmlUnit() {
		XMLUnit.setIgnoreAttributeOrder(true);
		XMLUnit.setIgnoreComments(true);
		XMLUnit.setIgnoreWhitespace(true);

	}

	@Test
	public void canMarshalPomFile() throws IOException, SAXException {
		MavenArtefact artefact = new MavenArtefact(new MavenGroup("com.crsn"),
				"boo", new MavenVersion(1,0), new File("."));
		PomFileContent content = new PomFileContent(artefact);
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		content.serializeContent(bos);

		String expected = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
				+ "<project xmlns=\"http://maven.apache.org/POM/4.0.0\">"
				+ "<groupId>com.crsn</groupId>"
				+ "<artifactId>boo</artifactId>"
				+ "<version>1.0</version>"
				+ "</project>";

		Reader expectedReader = new StringReader(expected);
		Reader generatedReader = new InputStreamReader(
				new ByteArrayInputStream(bos.toByteArray()));
		assertXMLEqual(expectedReader, generatedReader);

	}
}
