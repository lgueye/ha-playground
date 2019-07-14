package io.agileinfra.platform.scheduler.example.producer;

import io.agileinfra.platform.broker.client.PlatformBrokerClient;
import io.agileinfra.platform.broker.client.PlatformBrokerClientConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(PlatformBrokerClientConfiguration.class)
@Slf4j
public class PlatformSchedulerExampleProducerConfiguration {

	@Bean
	public PlatformSchedulerExampleProducerJob platformSchedulerExampleProducerJob(final PlatformBrokerClient brokerClient) {
		return new PlatformSchedulerExampleProducerJob(brokerClient);
	}
}
