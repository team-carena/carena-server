package org.sopt.carena.global.config;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

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
								.maxLifeTime(Duration.ofSeconds(5))
								.build()
				)
				.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 500)
				.responseTimeout(Duration.ofMillis(10000))
				.doOnConnected(conn -> conn
						.addHandlerLast(new ReadTimeoutHandler(10000, TimeUnit.MILLISECONDS))
						.addHandlerLast(new WriteTimeoutHandler(10000, TimeUnit.MILLISECONDS))
				);

		XmlMapper xmlMapper = new XmlMapper();
		xmlMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		return WebClient.builder()
				.clientConnector(new ReactorClientHttpConnector(httpClient))
				.codecs(configurer -> configurer
						.customCodecs()
						.register(new Jackson2JsonDecoder(xmlMapper, MediaType.APPLICATION_XML)))
				.build();
	}
}