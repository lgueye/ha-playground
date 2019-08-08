package io.agileinfra.platform.scheduler.server;

import com.hazelcast.core.ILock;
import io.agileinfra.platform.broker.client.PlatformBrokerClient;
import io.agileinfra.platform.cache.client.PlatformCacheClient;
import io.agileinfra.platform.dto.SensorEventScheduleRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

/**
 * @author louis.gueye@gmail.com
 */
@RequiredArgsConstructor
@Slf4j
public class NewScheduleRequestExecution implements Runnable {
	final SensorEventScheduleRequestDto request;
	final PlatformBrokerClient brokerClient;
	final PlatformCacheClient cacheClient;

	@Override
	public void run() {
		final String requestId = request.getId();
		final ILock lock = cacheClient.getLock(requestId);
		// Get lock as soon as possible
		if (!lock.tryLock()) {
			log.info("################## Ignored {}: request is already being executed (locked)", requestId);
			return;
		}
		// It might happen that the schedule is available for lock but just because another worker is already done processing
		// In which case the schedule will no longer be available in the store
		// On the other hand if the schedule is present then we can just proceed
		final Optional<SensorEventScheduleRequestDto> persistedOptional = cacheClient.getOne("schedules", requestId);
		if (!persistedOptional.isPresent()) {
			log.info("################## Request {} has already been executed", requestId);
			return;
		}
		brokerClient.publish(request.getMessage(), request.getExchange(), request.getRoutingKey());
		cacheClient.remove("schedules", requestId);
		cacheClient.unlock(lock);
	}
}
