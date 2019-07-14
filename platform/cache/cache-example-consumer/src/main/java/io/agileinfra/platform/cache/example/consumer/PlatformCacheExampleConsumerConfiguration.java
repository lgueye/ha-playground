package io.agileinfra.platform.cache.example.consumer;

import io.agileinfra.platform.cache.client.PlatformCacheClient;
import io.agileinfra.platform.cache.client.PlatformCacheClientConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(PlatformCacheClientConfiguration.class)
@Slf4j
public class PlatformCacheExampleConsumerConfiguration {

	@Bean
	public PlatformCacheExampleConsumerJob platformBrokerExampleConsumerJob(final PlatformCacheClient cacheClient) {
		return new PlatformCacheExampleConsumerJob(cacheClient);
	}
}
