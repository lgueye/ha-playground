package io.agileinfra.platform.broker.client;

import lombok.Builder;
import lombok.Data;

/**
 * Created by <a href="mailto:louis.gueye@domo-safety.com">Louis Gueye</a>.
 */
@Data
@Builder(toBuilder = true)
public class RoutingDto {
	private String id;
	private String key;

}
