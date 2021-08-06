package com.mq.poc.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@ConfigurationProperties(prefix = "mq.c.ibm.mq.pool")
@Data
@NoArgsConstructor
public class CloudpropC {

	private Integer maxConnections;
	private Integer idleTimeout;
	private Integer maxSessionsPerConnection;
	private Boolean blockIfFull;
	private Long blockIfFullTimeout;
	private Long timeBetweenExpirationCheck;
	private Long reconnectInterval;
	private Long maxInterval;
	private Float multiplier;
	private Boolean exponentialBackoff;

}
