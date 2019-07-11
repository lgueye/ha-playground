package io.agileinfra.platform.broker.client;

import com.google.common.collect.Sets;
import lombok.Data;

import java.util.Set;

/**
 * Created by <a href="mailto:louis.gueye@domo-safety.com">Louis Gueye</a>.
 */
@Data
public class QueuesConfig {
	private Set<ExchangeDto> exchanges = Sets.newHashSet();
}
