package io.agileinfra.platform.scheduler.server;

import io.agileinfra.platform.broker.client.PlatformBrokerClient;
import io.agileinfra.platform.cache.client.PlatformCacheClient;
import io.agileinfra.platform.dto.NewScheduleRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
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
@RequiredArgsConstructor
public class PlatformSchedulerScheduleRequestReactor {

	private final ScheduledExecutorService scheduledExecutorService;
	private final PlatformBrokerClient brokerClient;
	private final PlatformCacheClient cacheClient;

	@RabbitListener(bindings = @QueueBinding(value = @Queue, exchange = @Exchange(name = "careassist_schedules_topics", type = ExchangeTypes.FANOUT)))
	public void onMessage(NewScheduleRequestDto request) {
		log.info("<<<<<<<<<<<< Received request [" + request + "] from {}...", "careassist_schedules_topics");
		// final NewScheduleRequestDto detached = request.toBuilder().status(ScheduleStatus.pending).build();
		cacheClient.save("schedules", request);
		final Runnable runnable = new NewScheduleRequestExecution(request, brokerClient, cacheClient);
		scheduledExecutorService.schedule(runnable, Duration.between(Instant.now(), request.getTimestamp()).toMillis(), TimeUnit.MILLISECONDS);
	}
}
