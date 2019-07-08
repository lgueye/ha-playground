package io.agileinfra.platform.store.example;

import io.agileinfra.platform.store.client.PlatformStoreClientConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.sql.DataSource;

/**
 * @author louis.gueye@gmail.com
 */
@Configuration
@Import(PlatformStoreClientConfiguration.class)
public class PlatformStoreExampleConfiguration {

	@Bean
	public PlatformStoreExampleJob job(final DataSource dataSource) {
		return new PlatformStoreExampleJob(dataSource);
	}
}
