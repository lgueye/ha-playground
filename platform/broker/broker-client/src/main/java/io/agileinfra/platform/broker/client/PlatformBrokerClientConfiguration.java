package io.agileinfra.platform.broker.client;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

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
public class PlatformBrokerClientConfiguration {

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
	public PlatformBrokerClient brokerClient(final AmqpTemplate amqpTemplate) {
		return new PlatformBrokerClient(amqpTemplate);
	}

}
