package io.agileinfra.platform.broker.example.consumer;

import io.agileinfra.platform.broker.client.ScheduleDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Created by <a href="mailto:louis.gueye@domo-safety.com">Louis Gueye</a>.
 */
@Component
@Slf4j
@RabbitListener(queues = "#{schedulesQueue.name}")
@RequiredArgsConstructor
public class PlatformBrokerExampleSchedulesTopicConsumer {

	private final Queue schedulesQueue;

	@RabbitHandler
	public void onMessage(ScheduleDto schedule) {
		log.info("<<<<<<<<<<<< Received event [" + schedule + "] from {}...", schedulesQueue.getName());
	}
}
