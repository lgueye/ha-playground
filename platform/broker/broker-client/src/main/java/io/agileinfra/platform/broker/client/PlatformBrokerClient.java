package io.agileinfra.platform.broker.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;

/**
 * @author louis.gueye@gmail.com
 */
@RequiredArgsConstructor
@Slf4j
public class PlatformBrokerClient {
	private final AmqpTemplate amqpTemplate;

	public void publish(Object message, String exchange, String routingKey) {
		amqpTemplate.convertAndSend(exchange, routingKey, message);
		log.info("Published {} to exchange {} with routing key {}", message, exchange, routingKey);
	}
}
