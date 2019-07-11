package io.agileinfra.platform.broker.client;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Declarable;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.util.List;

/**
 * Queues configuration are shared by all consumers
 * Therefore we do not need to declare them in each consumer
 * This common setup applies to all of them
 * If the config is already present in rabbitmq it wont be created again
 *
 * @author louis.gueye@gmail.com
 */
@Configuration
@Slf4j
@RequiredArgsConstructor
@Import({TopicsConfig.class, QueuesConfig.class})
public class PlatformBrokerClientConfiguration {

	private final Environment environment;

	@Bean
	public MessageConverter messageConverter() {
		return new Jackson2JsonMessageConverter(Jackson2ObjectMapperBuilder.json() //
				.serializationInclusion(JsonInclude.Include.NON_NULL) // Don’t include null values
				.featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS) // use ISODate and not other cryptic formats
				.featuresToDisable(SerializationFeature.FAIL_ON_EMPTY_BEANS) // allow empty json to be produced (introduced with care alarm models
				// which can be as simple as `{}`
				.featuresToDisable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES) // do not fail on unknown properties because they are likely to
				.modules(new JavaTimeModule()) //
				.build());
	}

	@Bean
	public List<Declarable> directBindings(final QueuesConfig queuesConfig) {
		log.info("Creating Destinations...");
		final List<Declarable> declarables = Lists.newArrayList();
		queuesConfig.getExchanges().forEach(exchange -> {
			Exchange ex = ExchangeBuilder.directExchange(exchange.getId()).durable(true).build();
			declarables.add(ex);
			exchange.getRoutes().forEach(queue -> {
				Queue q = QueueBuilder.durable(queue.getId()).build();
				log.info("Successfully created queue {}.", queue.getId());
				declarables.add(q);
				Binding b = BindingBuilder.bind(q).to(ex).with(queue.getKey()).noargs();
				declarables.add(b);
				log.info("Successfully bound exchange {} to queue {} with routing key {}.", exchange.getId(), queue.getId(), queue.getKey());
			});
		});
		return declarables;
	}

	@Bean
	public List<Declarable> fanoutBindings(final TopicsConfig topicsConfig) {
		log.info("Creating Destinations...");
		final List<Declarable> declarables = Lists.newArrayList();
		topicsConfig.getExchanges().forEach(exchange -> {
			Exchange ex = ExchangeBuilder.fanoutExchange(exchange.getId()).durable(true).build();
			declarables.add(ex);
			exchange.getRoutes().forEach(queue -> {
				Queue q = QueueBuilder.nonDurable(queue.getId()).autoDelete().exclusive().build();
				log.info("Successfully created queue {}.", q.getName());
				declarables.add(q);
				Binding b = BindingBuilder.bind(q).to(ex).with(queue.getKey()).noargs();
				declarables.add(b);
				log.info("Successfully bound exchange {} to queue {} with routing key {}.", ex.getName(), q.getName(), b.getRoutingKey());
			});
		});
		return declarables;
	}

}
