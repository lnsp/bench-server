package com.bota.server.patch.system;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.sun.net.httpserver.HttpServer;

public class HttpPatchServer {
	public static Logger logger = Logger.getLogger("HttpPatchServer");

	public static void main(String[] args) throws IOException {
		Properties serverProperties = loadConfig();

		int port = Integer.parseInt(serverProperties.getProperty("server.port"));
		long updateRate = Long.parseLong(serverProperties.getProperty("cache.lifetime"));

		logger.setLevel(Level.ALL);
		logger.info("Update rate set to " + (updateRate / 1000) + "s");

		HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
		logger.info("server listen on port " + port);

		if (Boolean.parseBoolean(serverProperties.getProperty("package.context"))) {
			server.createContext("/package", new PatchHandler(updateRate));
			logger.info("/package context ready");
		}
		if (Boolean.parseBoolean(serverProperties.getProperty("hash.context"))) {
			server.createContext("/hash", new HashHandler(updateRate));
			logger.info("/hash context ready");
		}
		if (Boolean.parseBoolean(serverProperties.getProperty("files.context"))) {
			server.createContext("/files", new FilesHandler(updateRate));
			logger.info("/files context ready");
		}
		server.start();
	}
	public static Properties loadConfig() throws IOException {
		File configFile = new File("config.properties");
		if (!configFile.exists()) {
			configFile.createNewFile();
		}
		FileInputStream configInputStream = new FileInputStream(configFile);
		Properties configProperties = new Properties();
		configProperties.load(configInputStream);

		if (!configProperties.containsKey("package.context"))
			configProperties.put("package.context", "true");
		if (!configProperties.containsKey("hash.context"))
			configProperties.put("hash.context", "true");
		if (!configProperties.containsKey("files.context"))
			configProperties.put("files.context", "true");
		if (!configProperties.containsKey("cache.lifetime"))
			configProperties.put("cache.lifetime", "600");
		if (!configProperties.containsKey("server.port"))
			configProperties.put("server.port", "80");

		FileOutputStream configOutputStream = new FileOutputStream(configFile);
		configProperties.store(configOutputStream, "BotA Patch Server Properties File");

		return configProperties;

	}
}
