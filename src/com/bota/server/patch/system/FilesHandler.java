package com.bota.server.patch.system;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

import com.bota.server.patch.util.PathUtil;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class FilesHandler extends CachedHandler<HashMap<String, byte[]>> implements HttpHandler {

	public FilesHandler(long cacheUpdateRate) {
		super(cacheUpdateRate, Logger.getLogger("FilesHandler"));
	}
	@Override
	public void handle(HttpExchange httpExchange) throws IOException {
		testCache();
		
		httpExchange.getResponseHeaders().add("Content-type", "application/octet-stream");
		httpExchange.sendResponseHeaders(200, cacheData.get(httpExchange.getRequestURI().getPath()).length);
		try (OutputStream zipOutputStream = httpExchange.getResponseBody()) {
			zipOutputStream.write(cacheData.get(httpExchange.getRequestURI().getPath()));
		}
	}
	@Override
	public void reloadCache() throws IOException {
		cacheData = new HashMap<String, byte[]>();
		
		ArrayList<File> files = new ArrayList<File>();
		PathUtil.listf("files", files);
		
		for (File file : files) {
			byte[] data = new byte[(int)file.length()];
			FileInputStream zipInputStream = new FileInputStream(file);
			try (BufferedInputStream bufferedInputStream = new BufferedInputStream(zipInputStream)) {
				bufferedInputStream.read(data, 0, data.length);
			}
			cacheData.put("/" + file.getPath().replace("\\", "/"), data);
		}
	}

}
