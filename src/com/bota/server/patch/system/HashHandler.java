package com.bota.server.patch.system;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.List;
import java.util.logging.Logger;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class HashHandler extends CachedHandler<String> implements HttpHandler {
	public HashHandler(long cacheUpdateRate) {
		super(cacheUpdateRate, Logger.getLogger("HashHandler"));
	}
	@Override
	public void handle(HttpExchange httpExchange) throws IOException {
		testCache();
		httpExchange.getResponseHeaders().add("Content-type", "text/plain");
		httpExchange.sendResponseHeaders(200, cacheData.length());
		try (OutputStream outputStream = httpExchange.getResponseBody()) {
			outputStream.write(cacheData.getBytes());
		}
	}
	@Override
	public void reloadCache() throws IOException {
		StringBuilder builder = new StringBuilder();
		List<String> hashData = Files.readAllLines(FileSystems.getDefault().getPath("deploy/hash"));
		for (String line : hashData) {
			builder.append(line);
			builder.append(System.lineSeparator());
		}
		cacheData = builder.toString();
	}

}
