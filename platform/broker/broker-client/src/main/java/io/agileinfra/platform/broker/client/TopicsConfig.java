package io.agileinfra.platform.broker.client;

import com.google.common.collect.Lists;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by <a href="mailto:louis.gueye@domo-safety.com">Louis Gueye</a>.
 */
@Component
@ConfigurationProperties(prefix = "topics")
public class TopicsConfig {
	private List<ExchangeDto> exchanges;

	public List<ExchangeDto> getExchanges() {
		return exchanges;
	}

	public void setExchanges(List<ExchangeDto> exchanges) {
		this.exchanges = exchanges;
	}

	public static class ExchangeDto {
		private String id;
		private List<RoutingDto> routes = Lists.newArrayList();

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public List<RoutingDto> getRoutes() {
			return routes;
		}

		public void setRoutes(List<RoutingDto> routes) {
			this.routes = routes;
		}

		public static class RoutingDto {
			private String id;
			private String key;

			public String getId() {
				return id;
			}

			public void setId(String id) {
				this.id = id;
			}

			public String getKey() {
				return key;
			}

			public void setKey(String key) {
				this.key = key;
			}
		}
	}

}
