package com.test.stock.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Created by koseungbin on 2020-10-18
 */

@Configuration
public class RestTemplateConfig {
	@Bean
	public RestTemplate setUpRestTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}
}
