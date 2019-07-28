package io.agileinfra.platform.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;

/**
 * Created by <a href="mailto:louis.gueye@domo-safety.com">Louis Gueye</a>.
 */
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class NewScheduleRequestDto<T> implements Serializable, IdReader {
	private String id;
	private String exchange;
	private String routingKey;
	private T message;
	private ScheduleStatus status;
	private Instant timestamp;
}
