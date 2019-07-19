package io.agileinfra.platform.scheduler.server;

import com.google.common.collect.Lists;
import io.agileinfra.platform.broker.client.PlatformBrokerClientConfiguration;
import io.agileinfra.platform.broker.client.QueuesConfig;
import io.agileinfra.platform.broker.client.TopicsConfig;
import io.agileinfra.platform.cache.client.PlatformCacheClientConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Declarable;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Configuration
@Import({PlatformCacheClientConfiguration.class, PlatformBrokerClientConfiguration.class})
@Slf4j
public class PlatformSchedulerServerConfiguration {
	@Bean
	public ScheduledExecutorService scheduledExecutorService() {
		return Executors.newScheduledThreadPool(3);
	}


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
				Queue q = QueueBuilder.nonDurable().autoDelete().build();
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
