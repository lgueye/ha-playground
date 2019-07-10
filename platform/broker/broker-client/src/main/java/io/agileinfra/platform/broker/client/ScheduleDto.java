package io.agileinfra.platform.broker.client;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

/**
 * Created by <a href="mailto:louis.gueye@domo-safety.com">Louis Gueye</a>.
 */
@Data
@Builder(toBuilder = true)
public class ScheduleDto {
	private String id;
	final String destination;
	final Object message;
	private Instant timestamp;

}
