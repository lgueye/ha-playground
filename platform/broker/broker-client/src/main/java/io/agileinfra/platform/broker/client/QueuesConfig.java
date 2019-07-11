package io.agileinfra.platform.broker.client;

import com.google.common.collect.Sets;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.Set;

/**
 * Created by <a href="mailto:louis.gueye@domo-safety.com">Louis Gueye</a>.
 */
@Configuration
@ConfigurationProperties("queues")
@PropertySource("file:///var/platform/current/broker-example-consumer.yaml")
@Data
public class QueuesConfig {
	private Set<ExchangeDto> exchanges = Sets.newHashSet();
}
