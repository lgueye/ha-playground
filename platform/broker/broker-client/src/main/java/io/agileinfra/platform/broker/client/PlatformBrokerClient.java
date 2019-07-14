package io.agileinfra.platform.broker.client;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AmqpTemplate;

/**
 * @author louis.gueye@gmail.com
 */
@RequiredArgsConstructor
public class PlatformBrokerClient {
	private final AmqpTemplate amqpTemplate;

	public void publish(Object message, String exchange, String routingKey) {
		amqpTemplate.convertAndSend(exchange, routingKey, message);
	}
}
