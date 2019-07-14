package io.agileinfra.platform.scheduler.server;

import com.hazelcast.core.ILock;
import io.agileinfra.platform.broker.client.PlatformBrokerClient;
import io.agileinfra.platform.cache.client.PlatformCacheClient;
import io.agileinfra.platform.dto.NewScheduleRequestDto;
import io.agileinfra.platform.dto.ScheduleStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

/**
 * @author louis.gueye@gmail.com
 */
@RequiredArgsConstructor
@Slf4j
public class NewScheduleRequestExecution implements Runnable {
	final NewScheduleRequestDto request;
	final PlatformBrokerClient brokerClient;
	final PlatformCacheClient cacheClient;

	@Override
	public void run() {
		final String requestId = request.getId();
		Optional<NewScheduleRequestDto> persistedOptional = cacheClient.getOne("schedules", requestId);
		if (!persistedOptional.isPresent() || persistedOptional.get().getStatus() != ScheduleStatus.pending) {
			log.info("Request {} has already been executed", requestId);
			return;
		}
		final ILock lock = cacheClient.getLock(requestId);
		if (!lock.tryLock()) {
			log.info("Ignored {}: request is already being executed (locked)", requestId);
			return;
		}
		brokerClient.publish(request.getMessage(), request.getExchange(), request.getRoutingKey());
		cacheClient.remove("schedules", requestId);
		cacheClient.unlock(lock);
	}
}
