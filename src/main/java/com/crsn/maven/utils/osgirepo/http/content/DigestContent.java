package com.crsn.maven.utils.osgirepo.http.content;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Arrays;

import javax.crypto.Mac;
import javax.xml.crypto.dsig.spec.HMACParameterSpec;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.output.NullOutputStream;

import com.crsn.maven.utils.osgirepo.http.Content;

public class DigestContent implements Content {

	private final byte[] asciiEncodedMac;
	
	public DigestContent(String type, Content originalContent) {
		if (type == null) {
			throw new NullPointerException("Null type");
		}
		if (originalContent == null) {
			throw new NullPointerException("Null original content.");
		}
		
		MacOutputStream macStream = new MacOutputStream(type);
		try {
			originalContent.serializeContent(macStream);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		try {
			this.asciiEncodedMac=toHexString(macStream.getMac()).getBytes("ASCII");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	private String toHexString(byte[] mac) {
		StringBuilder builder=new StringBuilder();
		for (byte b : mac) {
			builder.append(String.format("%x",b));
		}
		return builder.toString();
	}

	@Override
	public String contentType() {
		return "text/plain";
	}

	@Override
	public long contentLength() {
		return asciiEncodedMac.length;
	}

	@Override
	public void serializeContent(OutputStream stream) throws IOException {
		stream.write(asciiEncodedMac);
	}
	
	private class MacOutputStream extends FilterOutputStream {

		private final MessageDigest digest;

		public MacOutputStream(String macType) {
			super(new NullOutputStream());
			try {
				this.digest = MessageDigest.getInstance(macType);
//				DigestUtils.md5Hex(data)
			} catch (NoSuchAlgorithmException e) {
				throw new IllegalArgumentException(e);
			} 
		}

		@Override
		public void write(int b) throws IOException {
			digest.update((byte) b);
		}
		
		public byte[] getMac() {
			return digest.digest();
		}
	}

}
