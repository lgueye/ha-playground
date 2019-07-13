package io.agileinfra.platform.cache.example.producer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author louis.gueye@gmail.com
 */
@SpringBootApplication
public class PlatformCacheExampleProducer {
	public static void main(String[] args) {
		SpringApplication.run(PlatformCacheExampleProducer.class, args).close();
	}
}
