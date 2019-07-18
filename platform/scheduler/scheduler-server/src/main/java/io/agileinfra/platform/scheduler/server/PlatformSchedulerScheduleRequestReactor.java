package io.agileinfra.platform.scheduler.server;

import io.agileinfra.platform.broker.client.PlatformBrokerClient;
import io.agileinfra.platform.cache.client.PlatformCacheClient;
import io.agileinfra.platform.dto.NewScheduleRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by <a href="mailto:louis.gueye@domo-safety.com">Louis Gueye</a>.
 */
@Component
@Slf4j
@RabbitListener(queues = "schedules")
@RequiredArgsConstructor
public class PlatformSchedulerScheduleRequestReactor {

	private final ScheduledExecutorService scheduledExecutorService;
	private final PlatformBrokerClient brokerClient;
	private final PlatformCacheClient cacheClient;

	@RabbitHandler
	public void onMessage(NewScheduleRequestDto request) {
		log.info("<<<<<<<<<<<< Received request [" + request + "] from {}...", "schedules");
		// final NewScheduleRequestDto detached = request.toBuilder().status(ScheduleStatus.pending).build();
		cacheClient.save("schedules", request);
		final Runnable runnable = new NewScheduleRequestExecution(request, brokerClient, cacheClient);
		scheduledExecutorService.schedule(runnable, Duration.between(Instant.now(), request.getTimestamp()).toMillis(), TimeUnit.MILLISECONDS);
	}
}
