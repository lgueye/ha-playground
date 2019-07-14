package io.agileinfra.platform.broker.example.consumer;

import io.agileinfra.platform.dto.NewScheduleRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Created by <a href="mailto:louis.gueye@domo-safety.com">Louis Gueye</a>.
 */
@Component
@Slf4j
@RabbitListener(queues = "schedules")
@RequiredArgsConstructor
public class PlatformBrokerExampleSchedulesTopicConsumer {

	@RabbitHandler
	public void onMessage(NewScheduleRequestDto schedule) {
		log.info("<<<<<<<<<<<< Received event [" + schedule + "] from {}...", "schedules");
	}
}
