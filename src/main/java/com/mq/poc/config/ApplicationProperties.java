package com.mq.poc.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ConfigurationProperties(prefix = "mq-bridge.rabbit.listener")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationProperties {

	private String autoStartup;
	private String concurrency;

}
