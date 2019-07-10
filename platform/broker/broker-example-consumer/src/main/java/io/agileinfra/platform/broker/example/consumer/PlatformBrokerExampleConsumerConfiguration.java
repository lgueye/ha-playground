package io.agileinfra.platform.broker.example.consumer;

import io.agileinfra.platform.broker.client.ExchangeDto;
import io.agileinfra.platform.broker.client.PlatformBrokerClientConfiguration;
import io.agileinfra.platform.broker.client.TopicsConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.Optional;

/**
 * Topics must be configured in the consumer.
 * The reason is because when multiple listeners listen to the same queue only one will get the message
 * In order to broadcast to all queues, we must use a unique queue name and listen to that queue
 * We therefore ensure that we are the only consumer listening to that queue
 *
 * Created by <a href="mailto:louis.gueye@domo-safety.com">Louis Gueye</a>.
 */
@Configuration
@Import(PlatformBrokerClientConfiguration.class)
@RequiredArgsConstructor
@Slf4j
public class PlatformBrokerExampleConsumerConfiguration {
	private final TopicsConfig topics;
	private final AmqpAdmin amqpAdmin;

	@Bean
	public Exchange fanoutExchange(final AmqpAdmin amqpAdmin) {
		final Optional<ExchangeDto> optional = topics.getExchanges().stream()
				.filter(exchange -> "careassist_schedules_topics".equals(exchange.getId())).findFirst();
		if (!optional.isPresent()) {
			throw new IllegalStateException("Missing <careassist_schedules_topics> configuration");
		}
		final Exchange exchange = ExchangeBuilder.fanoutExchange(optional.get().getId()).durable(true).build();
		amqpAdmin.declareExchange(exchange);
		return exchange;
	}

	@Bean
	public Queue schedulesQueue() {
		Queue q = QueueBuilder.nonDurable().build();
		amqpAdmin.declareQueue(q);
		log.info("Successfully created queue {}.", q.getName());
		return q;
	}

	@Bean
	public Binding schedulesBinding(final Exchange exchange, final Queue queue) {
		Binding b = BindingBuilder.bind(queue).to(exchange).with(queue.getName()).noargs();
		amqpAdmin.declareBinding(b);
		log.info("Successfully bound exchange {} to queue {} with routing key {}.", exchange.getName(), queue.getName(), b.getRoutingKey());
		return b;
	}

}
