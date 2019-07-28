package io.agileinfra.platform.broker.example.consumer;

import io.agileinfra.platform.dto.NewScheduleRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Created by <a href="mailto:louis.gueye@domo-safety.com">Louis Gueye</a>.
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class PlatformBrokerExampleSchedulesTopicConsumer {

	@RabbitListener(bindings = @QueueBinding(value = @Queue, exchange = @Exchange(name = "careassist_schedules_topics", type = ExchangeTypes.FANOUT)))
	public void onMessage(NewScheduleRequestDto schedule) {
		log.info("<<<<<<<<<<<< Received event [" + schedule + "] from {}...", "careassist_schedules_topics");
	}
}
