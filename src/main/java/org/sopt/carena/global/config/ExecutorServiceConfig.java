package org.sopt.carena.global.config;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExecutorServiceConfig {
	@Bean(destroyMethod = "shutdown")
	public ExecutorService virtualThreadExecutor() {
		return Executors.newVirtualThreadPerTaskExecutor();
	}
}
