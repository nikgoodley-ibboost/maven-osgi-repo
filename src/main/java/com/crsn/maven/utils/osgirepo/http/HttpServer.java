package com.crsn.maven.utils.osgirepo.http;

import java.io.IOException;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

import org.simpleframework.http.Path;
import org.simpleframework.http.Request;
import org.simpleframework.http.Response;
import org.simpleframework.http.core.Container;
import org.simpleframework.transport.connect.Connection;
import org.simpleframework.transport.connect.SocketConnection;

public class HttpServer implements Container {

	public static void main(String[] args) throws IOException {
		Container container = new HttpServer();
		Connection connection = new SocketConnection(container);
		SocketAddress address = new InetSocketAddress(8080);
		connection.connect(address);

	}

	@Override
	public void handle(Request req, Response res) {
		Path path = req.getPath();
		System.out.printf("path: %s%n", path.getPath());

		try {
			res.set("Content-Type", "text/plain");
			res.set("Server", "HelloWorld/1.0 (Simple 4.0)");
			res.setDate("Date", System.currentTimeMillis());
			res.setDate("Last-Modified", System.currentTimeMillis());

			PrintStream ps = res.getPrintStream();
			ps.println("Hello world!");
			ps.close();

		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

}
