package io.agileinfra.platform.cache.example.consumer;

import io.agileinfra.platform.cache.client.PlatformCacheClient;
import io.agileinfra.platform.dto.SensorEventDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;

import java.util.Map;

/**
 * @author louis.gueye@gmail.com
 */
@RequiredArgsConstructor
@Slf4j
public class PlatformCacheExampleConsumerJob implements CommandLineRunner {

	private final PlatformCacheClient cacheClient;

	@Override
	public void run(String... args) {
		Map<String, SensorEventDto> map = cacheClient.getMap("events");
		map.forEach((k, v) -> log.info("k: {}, v: {}", k, v));
	}
}
