package com.bota.server.patch.system;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Logger;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class PatchHandler extends CachedHandler<byte[]> implements HttpHandler {
	public PatchHandler(long cacheUpdateRate) {
		super(cacheUpdateRate, Logger.getLogger("PatchHandler"));
	}
	@Override
	public void handle(HttpExchange httpExchange) throws IOException {
		testCache();
		httpExchange.getResponseHeaders().add("Content-type", "application/zip");
		httpExchange.sendResponseHeaders(200, cacheData.length);
		try (OutputStream zipOutputStream = httpExchange.getResponseBody()) {
			zipOutputStream.write(cacheData);
		}
	}
	@Override
	public void reloadCache() throws IOException {
		File file = new File("deploy/patch.zip");
		cacheData = new byte [(int)file.length()];
		FileInputStream zipInputStream = new FileInputStream(file);
		try (BufferedInputStream bufferedInputStream = new BufferedInputStream(zipInputStream)) {
			bufferedInputStream.read(cacheData, 0, cacheData.length);
		}
	}

}
