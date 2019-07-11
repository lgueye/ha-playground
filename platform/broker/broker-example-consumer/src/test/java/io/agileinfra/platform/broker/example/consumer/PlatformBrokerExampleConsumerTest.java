package io.agileinfra.platform.broker.example.consumer;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by <a href="mailto:louis.gueye@domo-safety.com">Louis Gueye</a>.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = PlatformBrokerExampleConsumer.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application.yaml")
public class PlatformBrokerExampleConsumerTest {

	@Ignore
	@Test
	public void test() {

	}
}