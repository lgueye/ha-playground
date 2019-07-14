package io.agileinfra.platform.broker.example.consumer;

import io.agileinfra.platform.dto.SensorEventDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Created by <a href="mailto:louis.gueye@domo-safety.com">Louis Gueye</a>.
 */
@Component
@RabbitListener(queues = {PlatformBrokerExampleCareEventsQueueConsumer.QUEUE_NAME})
@Slf4j
public class PlatformBrokerExampleCareEventsQueueConsumer {
	public static final String QUEUE_NAME = "care_events";
	@RabbitHandler
	public void onMessage(SensorEventDto event) {
		log.info("<<<<<<<<<<<< Received event [" + event + "] from {}...", PlatformBrokerExampleCareEventsQueueConsumer.QUEUE_NAME);
	}
}
