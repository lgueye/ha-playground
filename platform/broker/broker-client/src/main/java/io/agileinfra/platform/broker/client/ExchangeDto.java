package io.agileinfra.platform.broker.client;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

/**
 * Created by <a href="mailto:louis.gueye@domo-safety.com">Louis Gueye</a>.
 */
@Data
@Builder(toBuilder = true)
public class ExchangeDto {
	private String id;
	private Set<RoutingDto> routes;

}
