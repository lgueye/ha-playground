package io.agileinfra.platform.broker.client;

import com.google.common.collect.Sets;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.Set;

/**
 * Created by <a href="mailto:louis.gueye@domo-safety.com">Louis Gueye</a>.
 */
@Data
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
public class TopicsConfig {
	private Set<ExchangeDto> exchanges = Sets.newHashSet();
}
