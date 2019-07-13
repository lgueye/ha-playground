package io.agileinfra.platform.cache.client;

import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.config.XmlClientConfigBuilder;
import com.hazelcast.core.HazelcastInstance;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.IOException;

/**
 * @author louis.gueye@gmail.com
 */
@Configuration
@Slf4j
public class PlatformCacheClientConfiguration {
    @Bean
    public HazelcastInstance getHazelcastClientInstance(@Value("${spring.hazelcast.config}") final File configFile) throws IOException {
        ClientConfig clientConfig = new XmlClientConfigBuilder(configFile).build();
        return com.hazelcast.client.HazelcastClient.newHazelcastClient(clientConfig);
    }}
