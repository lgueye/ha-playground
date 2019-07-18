package io.agileinfra.platform.scheduler.example.producer;

import io.agileinfra.platform.broker.client.PlatformBrokerClient;
import io.agileinfra.platform.dto.NewScheduleRequestDto;
import io.agileinfra.platform.dto.SensorEventDto;
import io.agileinfra.platform.dto.SensorState;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;
import java.util.stream.IntStream;

/**
 * @author louis.gueye@gmail.com
 */
@RequiredArgsConstructor
@Slf4j
public class PlatformSchedulerExampleProducerJob implements CommandLineRunner {

	private final PlatformBrokerClient brokerClient;

	@Override
	public void run(String... args) {
		final Instant now = Instant.now();
		final Instant anHourAgo = now.minus(Duration.ofHours(1));
		final String sensorBusinessId = "sens-q7ikjxk1ftik";
		final String targetExchangeName = "careassist_queues";
		final String fanoutExchangeName = "careassist_schedules_topics";
		final String targetRoutingKey = "care.events";
		IntStream.range(0, 2).boxed().forEach(i -> {
			final SensorEventDto event = SensorEventDto.builder() //
					.id(UUID.randomUUID().toString()) //
					.businessId(sensorBusinessId) //
					.timestamp(anHourAgo.plus(Duration.ofMinutes(i))) //
					.state(i % 2 == 0 ? SensorState.off : SensorState.on) //
					.build();
			final NewScheduleRequestDto schedule = NewScheduleRequestDto.builder().id(UUID.randomUUID().toString()) //
					.exchange(targetExchangeName) //
					.routingKey(targetRoutingKey) //
					.message(event) //
					.timestamp(now.plus(Duration.ofSeconds(i + 3))) //
					.build();
			brokerClient.publish(schedule, fanoutExchangeName, "#");
			log.info(">>>>>>>>>>> Sent {} to exchange {} with routing key {}", event.getId(), fanoutExchangeName, "#");
		});
	}
}
