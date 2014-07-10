package com.bota.server.patch.system;

import java.io.IOException;
import java.util.logging.Logger;

import com.sun.net.httpserver.HttpHandler;

public abstract class CachedHandler<T> implements HttpHandler {
	protected long cacheUpdateRate;
	protected T cacheData;
	protected long lastCacheUpdate;
	private Logger logger;
	
	public CachedHandler(long cacheUpdateRate, Logger logger) {
		this.cacheUpdateRate = cacheUpdateRate;
		this.logger = logger;
	}
	public abstract void reloadCache() throws IOException;
	public void testCache() throws IOException {
		if (System.currentTimeMillis() - lastCacheUpdate > cacheUpdateRate) {
			reloadCache();
			logger.info("Cache reloaded");
			lastCacheUpdate = System.currentTimeMillis();
		}
	}
	
}
