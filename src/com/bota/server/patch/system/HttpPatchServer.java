package com.bota.server.patch.system;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.sun.net.httpserver.HttpServer;

public class HttpPatchServer {
	public static Logger logger = Logger.getLogger("HttpPatchServer");

	public static void main(String[] args) throws IOException {
		if (args.length < 2) {
			System.err.println("Need arguments: <port> <update rate (in ms)>");
			return;
		}
		int port = Integer.parseInt(args[0]);
		long updateRate = Long.parseLong(args[1]);
		logger.setLevel(Level.ALL);
		logger.info("Update rate set to " + (updateRate / 1000) + "s");

		HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
		logger.info("server listen on port " + port);

		server.createContext("/package", new PatchHandler(updateRate));
		logger.info("/package context ready");

		server.createContext("/hash", new HashHandler(updateRate));
		logger.info("/hash context ready");

		server.createContext("/files", new FilesHandler(updateRate));
		logger.info("/files context ready");

		server.start();
	}

}
