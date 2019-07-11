package io.agileinfra.platform.broker.example.consumer;

import io.agileinfra.platform.broker.client.PlatformBrokerClientConfiguration;
import io.agileinfra.platform.broker.client.TopicsConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Topics must be configured in the consumer.
 * The reason is because when multiple listeners listen to the same queue only one will get the message
 * In order to broadcast to all queues, we must use a unique queue name and listen to that queue
 * We therefore ensure that we are the only consumer listening to that queue
 *
 * Created by <a href="mailto:louis.gueye@domo-safety.com">Louis Gueye</a>.
 */
@Configuration
@Import({PlatformBrokerClientConfiguration.class, TopicsConfig.class})
@Slf4j
public class PlatformBrokerExampleConsumerConfiguration {
}
