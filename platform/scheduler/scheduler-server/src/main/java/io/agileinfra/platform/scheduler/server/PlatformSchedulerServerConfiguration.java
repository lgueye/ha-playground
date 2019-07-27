package io.agileinfra.platform.scheduler.server;

import io.agileinfra.platform.broker.client.PlatformBrokerClientConfiguration;
import io.agileinfra.platform.cache.client.PlatformCacheClientConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

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

}
