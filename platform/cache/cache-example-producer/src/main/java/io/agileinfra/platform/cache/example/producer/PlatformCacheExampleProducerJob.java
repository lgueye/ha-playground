package io.agileinfra.platform.cache.example.producer;

import com.hazelcast.core.HazelcastInstance;
import io.agileinfra.platform.cache.client.SensorEventDto;
import io.agileinfra.platform.cache.client.SensorState;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;
import java.util.stream.IntStream;

/**
 * @author louis.gueye@gmail.com
 */
@RequiredArgsConstructor
@Slf4j
public class PlatformCacheExampleProducerJob implements CommandLineRunner {

	private final HazelcastInstance hazelcastInstance;

	@Override
	public void run(String... args) {
		final Instant now = Instant.now();
		final Instant anHourAgo = now.minus(Duration.ofHours(1));
		final String sensorBusinessId = "sens-q7ikjxk1ftik";
		Map<String, SensorEventDto> map = hazelcastInstance.getMap("events");
		IntStream.range(0, 60).boxed().forEach(i -> {
			final String id = UUID.randomUUID().toString();
			final SensorEventDto event = SensorEventDto.builder() //
					.id(id) //
					.businessId(sensorBusinessId) //
					.timestamp(anHourAgo.plus(Duration.ofMinutes(i))) //
					.state(SensorState.on) //
					.build();
			map.put(id, event);
			log.info(">>>>>>>>>>> Put k: {}, v: {} in map", event.getId(), event);
		});
	}
}
