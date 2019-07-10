package io.agileinfra.platform.broker.example.producer;

import io.agileinfra.platform.broker.client.ScheduleDto;
import io.agileinfra.platform.broker.client.SensorEventDto;
import io.agileinfra.platform.broker.client.SensorState;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
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
public class PlatformBrokerExampleProducerJob implements CommandLineRunner {

	private final AmqpTemplate template;

	@Override
	public void run(String... args) {
		final Instant now = Instant.now();
		final Instant anHourAgo = now.minus(Duration.ofHours(1));
		IntStream.range(0, 60).boxed().forEach(i -> {
			final SensorEventDto event = SensorEventDto.builder() //
					.id(UUID.randomUUID().toString()) //
					.businessId("sens-q7ikjxk1ftik") //
					.timestamp(anHourAgo.plus(Duration.ofMinutes(i))) //
					.state(SensorState.on) //
					.build();
			template.convertAndSend("careassist_queues", "care.events", event);
		});
		IntStream.range(0, 60).boxed().forEach(i -> {
			final SensorEventDto event = SensorEventDto.builder() //
					.id(UUID.randomUUID().toString()) //
					.businessId("sens-q7ikjxk1ftik") //
					.timestamp(anHourAgo.plus(Duration.ofMinutes(i))) //
					.state(SensorState.off) //
					.build();
			template.convertAndSend("careassist_queues", "maintenance.events", event);
		});
		IntStream.range(0, 60).boxed().forEach(i -> {
			final SensorEventDto event = SensorEventDto.builder() //
					.id(UUID.randomUUID().toString()) //
					.businessId("sens-q7ikjxk1ftik") //
					.timestamp(anHourAgo.plus(Duration.ofMinutes(i))) //
					.state(SensorState.off) //
					.build();
			final ScheduleDto schedule = ScheduleDto.builder().id(UUID.randomUUID().toString()) //
					.destination("care.events") //
					.message(event) //
					.timestamp(anHourAgo.plus(Duration.ofMinutes(i))) //
					.build();
			template.convertAndSend("careassist_schedules_topics", "ignored_anyway", schedule);
		});
	}
}
