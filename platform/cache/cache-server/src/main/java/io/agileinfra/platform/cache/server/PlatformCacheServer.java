package io.agileinfra.platform.cache.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author louis.gueye@gmail.com
 */
@SpringBootApplication
public class PlatformCacheServer {
	public static void main(String[] args) {
		SpringApplication.run(PlatformCacheServer.class, args);
	}
}
