package com.crsn.maven.utils.osgirepo.http.content;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

import com.crsn.maven.utils.osgirepo.http.Content;
import com.crsn.maven.utils.osgirepo.http.StringContent;
import com.crsn.maven.utils.osgirepo.maven.MavenRepository;

public class MacContentTest {

	@Test
	public void canCalculateMd5Mac() throws IOException {

		assertEquals("08c5926ca861023c1d2a36653fd88e2", createMacOfType("MD5"));
	}

	@Test
	public void canCalculateSha1Mac() throws IOException {

		assertEquals("d869db7fe62fb07c25a0403ecaea5531744b5fb",
				createMacOfType("SHA1"));
	}

	private static String createMacOfType(String digestType)
			throws IOException, UnsupportedEncodingException {
		Content originalContent = createTestContent();
		DigestContent content = new DigestContent(digestType, originalContent);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		content.serializeContent(baos);
		String contentAsString = baos.toString("ASCII");
		return contentAsString;
	}

	private static Content createTestContent() {
		return new StringContent("whatever", "ASCII", "text/plain");
	}

}
