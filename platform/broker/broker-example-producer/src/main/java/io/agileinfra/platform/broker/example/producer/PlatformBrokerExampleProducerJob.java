package io.agileinfra.platform.broker.example.producer;

import io.agileinfra.platform.broker.client.PlatformBrokerClient;
import io.agileinfra.platform.dto.SensorEventScheduleRequestDto;
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
public class PlatformBrokerExampleProducerJob implements CommandLineRunner {

	private final PlatformBrokerClient brokerClient;

	@Override
	public void run(String... args) {
		final Instant now = Instant.now();
		final Instant anHourAgo = now.minus(Duration.ofHours(1));
		final String directExchangeName = "careassist_queues";
		final String fanoutExchangeName = "careassist_schedules_topics";
		final String sensorBusinessId = "sens-q7ikjxk1ftik";
		IntStream.range(0, 60).boxed().forEach(i -> {
			final SensorEventDto event = SensorEventDto.builder() //
					.id(UUID.randomUUID().toString()) //
					.businessId(sensorBusinessId) //
					.timestamp(anHourAgo.plus(Duration.ofMinutes(i))) //
					.state(i % 2 == 0 ? SensorState.off : SensorState.on) //
					.build();
			final String routingKey = "care.events";
			brokerClient.publish(event, directExchangeName, routingKey);
			log.info(">>>>>>>>>>> Sent {} to exchange {} with routing key {}", event.getId(), directExchangeName, routingKey);
		});
		IntStream.range(0, 60).boxed().forEach(i -> {
			final SensorEventDto event = SensorEventDto.builder() //
					.id(UUID.randomUUID().toString()) //
					.businessId(sensorBusinessId) //
					.timestamp(anHourAgo.plus(Duration.ofMinutes(i))) //
					.state(i % 2 == 0 ? SensorState.off : SensorState.on) //
					.build();
			final String routingKey = "maintenance.events";
			brokerClient.publish(event, directExchangeName, routingKey);
			log.info(">>>>>>>>>>> Sent {} to exchange {} with routing key {}", event.getId(), directExchangeName, routingKey);
		});
		IntStream.range(0, 60).boxed().forEach(i -> {
			final SensorEventDto event = SensorEventDto.builder() //
					.id(UUID.randomUUID().toString()) //
					.businessId(sensorBusinessId) //
					.timestamp(anHourAgo.plus(Duration.ofMinutes(i))) //
					.state(i % 2 == 0 ? SensorState.off : SensorState.on) //
					.build();
			final SensorEventScheduleRequestDto schedule = SensorEventScheduleRequestDto.builder().id(UUID.randomUUID().toString()) //
					.exchange(directExchangeName).routingKey("care.events").message(event) //
					.timestamp(now.plus(Duration.ofMinutes(i))) //
					.build();
			final String routingKey = ".";
			brokerClient.publish(schedule, fanoutExchangeName, routingKey);
			log.info(">>>>>>>>>>> Sent {} to exchange {} with routing key {}", event.getId(), fanoutExchangeName, routingKey);
		});
	}
}
