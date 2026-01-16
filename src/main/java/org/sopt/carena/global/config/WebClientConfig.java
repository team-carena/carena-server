package org.sopt.carena.global.config;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;

@Configuration
public class WebClientConfig {
	@Bean
	public WebClient webClient() {

		HttpClient httpClient = HttpClient.create(
						ConnectionProvider.builder("external-api-connection-provider")
								.maxConnections(20)
								.maxIdleTime(Duration.ofMillis(5000))
								.maxLifeTime(Duration.ofMillis(5000))
								.build()
				)
				.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 500)
				.responseTimeout(Duration.ofMillis(30000))
				.doOnConnected(conn -> conn
						.addHandlerLast(new ReadTimeoutHandler(5000, TimeUnit.MILLISECONDS))
						.addHandlerLast(new WriteTimeoutHandler(5000, TimeUnit.MILLISECONDS))
				);

		return WebClient.builder()
				.clientConnector(new ReactorClientHttpConnector(httpClient))
				.build();
	}
}