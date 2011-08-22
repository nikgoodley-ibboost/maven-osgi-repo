package com.crsn.maven.utils.osgirepo.http;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.concurrent.ConcurrentHashMap;

import org.simpleframework.http.Path;
import org.simpleframework.http.Request;
import org.simpleframework.http.Response;
import org.simpleframework.http.core.Container;
import org.simpleframework.transport.connect.Connection;
import org.simpleframework.transport.connect.SocketConnection;

public class HttpServer implements Container {

	private enum State {
		NOT_RUNNING, RUNNING;
	}

	private ConcurrentHashMap<String, Content> contents = new ConcurrentHashMap<String, Content>();

	private State currentState = State.NOT_RUNNING;

	private final int port;

	private Connection connection;

	private InetSocketAddress listeningOn;

	public HttpServer() {
		this.port = 0;
	}

	public HttpServer(int port) {
		this.port = port;

	}

	public String getServerUrl() {
		checkState(State.RUNNING);
		return String.format("http://%s:%d/", listeningOn.getHostName(),
				listeningOn.getPort());

	}

	public void start() {
		try {
			connection = new SocketConnection(this);
			listeningOn = (InetSocketAddress) connection
					.connect(new InetSocketAddress(port));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		switchToState(State.RUNNING);
	}

	private void switchToState(State newState) {
		this.currentState = newState;

	}

	public void stop() {
		checkState(State.RUNNING);
		try {
			this.connection.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		switchToState(State.NOT_RUNNING);

	}

	private void checkState(State expected) {
		if (currentState != expected) {
			throw new IllegalStateException(String.format(
					"Server in illegal state %s, expected %s.", currentState,
					expected));
		}
	}

	public void registerContent(String path, Content content) {
		if (path == null) {
			throw new NullPointerException("Null path.");
		}
		if (content == null) {
			throw new NullPointerException("Null content.");
		}
		contents.put(path, content);
	}

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
			Content content = contents.get(path.toString());
			if (content != null) {
				res.set("Content-Type", content.contentType());
				res.set("Server", "HelloWorld/1.0 (Simple 4.0)");
				res.setDate("Date", System.currentTimeMillis());
				res.setDate("Last-Modified", System.currentTimeMillis());
				OutputStream outputStream = res.getOutputStream();
				content.serializeContent(outputStream);
				outputStream.close();
			} else {

				res.setCode(404);
				res.set("Content-Type", "text/plain");
				res.set("Server", "HelloWorld/1.0 (Simple 4.0)");
				res.setDate("Date", System.currentTimeMillis());
				res.setDate("Last-Modified", System.currentTimeMillis());

				PrintStream ps = res.getPrintStream();
				ps.println("Hello world!");
				ps.close();

			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

}
