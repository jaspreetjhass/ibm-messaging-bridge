package com.mq.poc.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Data;
import lombok.NoArgsConstructor;

@Configuration
@Data
@NoArgsConstructor
public class CommonCloudConfig {

	@Value("${ibm.mq.connName}")
	private String ibmMQConnectionString;
	@Value("${ibm.mq.queueManager}")
	private String queueManager;
	@Value("${ibm.mq.channel}")
	private String channel;
	@Value("${ibm.mq.user}")
	private String user;
	@Value("${ibm.mq.password}")
	private String password;

}
