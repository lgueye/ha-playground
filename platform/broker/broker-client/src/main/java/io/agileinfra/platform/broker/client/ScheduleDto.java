package io.agileinfra.platform.broker.client;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * Created by <a href="mailto:louis.gueye@domo-safety.com">Louis Gueye</a>.
 */
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
public class ScheduleDto {
	private String id;
	private String destination;
	private Object message;
	private Instant timestamp;

}
