package com.crsn.maven.utils.osgirepo.http.content;

import static org.custommonkey.xmlunit.XMLAssert.assertXMLEqual;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.Collections;
import java.util.List;

import org.custommonkey.xmlunit.XMLUnit;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import com.crsn.maven.utils.osgirepo.maven.MavenArtifact;
import com.crsn.maven.utils.osgirepo.maven.MavenDependency;
import com.crsn.maven.utils.osgirepo.maven.MavenRepository;
import com.crsn.maven.utils.osgirepo.maven.MavenVersion;

public class PomContentTest {

	@Before
	public void setUpXmlUnit() {
		XMLUnit.setIgnoreAttributeOrder(true);
		XMLUnit.setIgnoreComments(true);
		XMLUnit.setIgnoreWhitespace(true);

	}

	@Test
	public void canMarshalPomFile() throws IOException, SAXException {
		MavenArtifact artefact = new MavenArtifact("com.crsn",
				"boo", new MavenVersion(1, 0),
				Collections.<MavenDependency> emptyList(), new File("."));
		PomContent content = new PomContent(artefact);
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		content.serializeContent(bos);

		String expected = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
				+ "<project xmlns=\"http://maven.apache.org/POM/4.0.0\">"
				+ "<modelVersion>4.0.0</modelVersion>"
				+ "<groupId>com.crsn</groupId>"
				+ "<artifactId>boo</artifactId>"
				+ "<version>1.0</version>"
				+ "</project>";

		Reader expectedReader = new StringReader(expected);
		Reader generatedReader = new InputStreamReader(
				new ByteArrayInputStream(bos.toByteArray()));
		assertXMLEqual(expectedReader, generatedReader);

	}

	@Test
	public void canMarshalPomFileWithDependencies() throws IOException,
			SAXException {
		MavenRepository repository = ContentTestUtil
				.createMockMavenRepository();

		List<MavenArtifact> artefacts = repository.getArtefacts();

		MavenArtifact artefact = artefacts.get(0);

		PomContent content = new PomContent(artefact);
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		content.serializeContent(bos);

		String expected = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
				+ "<project xmlns=\"http://maven.apache.org/POM/4.0.0\">"
				+ "<modelVersion>4.0.0</modelVersion>"
				+ "<groupId>com.crsn</groupId>"
				+ "<artifactId>boo</artifactId>"
				+ "<version>1.0</version>"
				+ "<dependencies>"
				+ "<dependency>"
				+ "<artifactId>dependency</artifactId>"
				+ "<groupId>com.crsn</groupId>"
				+ "<version>[1.0,)</version>"
				+ "</dependency>"
				+ "<dependency>"
				+ "<artifactId>dependency</artifactId>"
				+ "<groupId>com.crsn</groupId>"
				+ "<version>LATEST</version>"
				+ "</dependency>"
				+ "</dependencies>" + "</project>";

		Reader expectedReader = new StringReader(expected);
		Reader generatedReader = new InputStreamReader(
				new ByteArrayInputStream(bos.toByteArray()));
		assertXMLEqual(expectedReader, generatedReader);

	}

}
