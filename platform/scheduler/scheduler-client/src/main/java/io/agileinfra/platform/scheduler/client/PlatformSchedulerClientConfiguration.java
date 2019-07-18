package io.agileinfra.platform.scheduler.client;

import io.agileinfra.platform.broker.client.PlatformBrokerClientConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author louis.gueye@gmail.com
 */
@Configuration
@Import(PlatformBrokerClientConfiguration.class)
@Slf4j
public class PlatformSchedulerClientConfiguration {
}
