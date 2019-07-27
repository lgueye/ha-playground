package io.agileinfra.platform.broker.example.consumer;

import io.agileinfra.platform.dto.NewScheduleRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

/**
 * Created by <a href="mailto:louis.gueye@domo-safety.com">Louis Gueye</a>.
 */
@Component
@Slf4j
@RabbitListener(autoStartup = "false", bindings = @QueueBinding(value = @Queue, exchange = @Exchange(name = "careassist_schedules_topics", type = ExchangeTypes.FANOUT)))
@RequiredArgsConstructor
public class PlatformBrokerExampleSchedulesTopicConsumer {

	@RabbitHandler
	public void onMessage(NewScheduleRequestDto schedule) {
		log.info("<<<<<<<<<<<< Received event [" + schedule + "] from {}...", "schedules");
	}
}
