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
public class SensorEventDto implements Serializable, IdReader {
	private String id;
	private String businessId;
	private SensorState state;
	private Instant insertedAt;
	private Instant timestamp;
}
