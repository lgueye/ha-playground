package io.agileinfra.platform.broker.client;

import com.google.common.collect.Sets;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * Created by <a href="mailto:louis.gueye@domo-safety.com">Louis Gueye</a>.
 */
@Component
@ConfigurationProperties("queues")
@Data
public class QueuesConfig {
	private Set<ExchangeDto> exchanges = Sets.newHashSet();
}
