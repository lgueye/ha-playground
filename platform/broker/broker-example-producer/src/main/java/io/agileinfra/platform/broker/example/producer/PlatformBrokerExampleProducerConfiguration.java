package io.agileinfra.platform.broker.example.producer;

import io.agileinfra.platform.broker.client.PlatformBrokerClientConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created by <a href="mailto:louis.gueye@domo-safety.com">Louis Gueye</a>.
 */
@Configuration
@Import(PlatformBrokerClientConfiguration.class)
public class PlatformBrokerExampleProducerConfiguration {

}
