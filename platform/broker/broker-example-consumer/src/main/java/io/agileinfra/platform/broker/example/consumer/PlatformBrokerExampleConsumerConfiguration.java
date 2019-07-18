package io.agileinfra.platform.broker.example.consumer;

import com.google.common.collect.Lists;
import io.agileinfra.platform.broker.client.PlatformBrokerClientConfiguration;
import io.agileinfra.platform.broker.client.QueuesConfig;
import io.agileinfra.platform.broker.client.TopicsConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.List;

/**
 * Topics must be configured in the consumer.
 * The reason is because when multiple listeners listen to the same queue only one will get the message
 * In order to broadcast to all queues, we must use a unique queue name and listen to that queue
 * We therefore ensure that we are the only consumer listening to that queue
 *
 * Created by <a href="mailto:louis.gueye@domo-safety.com">Louis Gueye</a>.
 */
@Configuration
@Import({PlatformBrokerClientConfiguration.class})
@Slf4j
public class PlatformBrokerExampleConsumerConfiguration {

	@Bean
	public QueuesConfig queuesConfig() {
		return new QueuesConfig();
	}

	@Bean
	public List<Declarable> directBindings(final AmqpAdmin amqpAdmin, final QueuesConfig queuesConfig) {
		log.info("Creating Destinations...");
		final List<Declarable> declarables = Lists.newArrayList();
		queuesConfig.getExchanges().forEach(exchange -> {
			Exchange ex = ExchangeBuilder.directExchange(exchange.getId()).durable(true).build();
			amqpAdmin.declareExchange(ex);
			declarables.add(ex);
			exchange.getRoutes().forEach(queue -> {
				Queue q = QueueBuilder.durable(queue.getId()).build();
				log.info("Successfully created queue {}.", queue.getId());
				amqpAdmin.declareQueue(q);
				declarables.add(q);
				Binding b = BindingBuilder.bind(q).to(ex).with(queue.getKey()).noargs();
				declarables.add(b);
				amqpAdmin.declareBinding(b);
				log.info("Successfully bound exchange {} to queue {} with routing key {}.", exchange.getId(), queue.getId(), queue.getKey());
			});
		});
		return declarables;
	}

	@Bean
	public TopicsConfig topicsConfig() {
		return new TopicsConfig();
	}

	@Bean
	public List<Declarable> fanoutBindings(final AmqpAdmin amqpAdmin, final TopicsConfig topicsConfig) {
		log.info("Creating Destinations...");
		final List<Declarable> declarables = Lists.newArrayList();
		topicsConfig.getExchanges().forEach(exchange -> {
			Exchange ex = ExchangeBuilder.fanoutExchange(exchange.getId()).durable(true).build();
			declarables.add(ex);
			amqpAdmin.declareExchange(ex);
			exchange.getRoutes().forEach(queue -> {
				Queue q = QueueBuilder.nonDurable(queue.getId()).autoDelete().build();
				log.info("Successfully created queue {}.", q.getName());
				declarables.add(q);
				amqpAdmin.declareQueue(q);
				Binding b = BindingBuilder.bind(q).to(ex).with(queue.getKey()).noargs();
				amqpAdmin.declareBinding(b);
				declarables.add(b);
				log.info("Successfully bound exchange {} to queue {} with routing key {}.", ex.getName(), q.getName(), b.getRoutingKey());
			});
		});
		return declarables;
	}
}
