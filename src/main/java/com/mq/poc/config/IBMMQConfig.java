package com.mq.poc.config;

import javax.jms.JMSException;

import org.messaginghub.pooled.jms.JmsPoolConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.transaction.RabbitTransactionManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.util.backoff.ExponentialBackOff;
import org.springframework.util.backoff.FixedBackOff;

import com.ibm.mq.jms.MQConnectionFactory;
import com.ibm.msg.client.wmq.WMQConstants;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class IBMMQConfig {

	@Scope("prototype")
	@Bean
	public javax.jms.ConnectionFactory mqConnectionFactory(final CommonCloudConfig commonCloudConfig)
			throws JMSException {
		final MQConnectionFactory mqConnectionFactory = new MQConnectionFactory();
		mqConnectionFactory.setConnectionNameList(commonCloudConfig.getIbmMQConnectionString());
		mqConnectionFactory.setChannel(commonCloudConfig.getChannel());
		mqConnectionFactory.setQueueManager(commonCloudConfig.getQueueManager());
		mqConnectionFactory.put(WMQConstants.USER_AUTHENTICATION_MQCSP, true);
		mqConnectionFactory.put(WMQConstants.USERID, commonCloudConfig.getUser());
		mqConnectionFactory.put(WMQConstants.PASSWORD, commonCloudConfig.getPassword());
		mqConnectionFactory.put(WMQConstants.WMQ_CONNECTION_MODE, 1);
		return mqConnectionFactory;
	}

	@Primary
	@Bean("jmsPoolConnectionFactoryA")
	public javax.jms.ConnectionFactory jmsPoolConnectionFactoryA(final CloudpropA cloudpropA,
			final javax.jms.ConnectionFactory mqConnectionFactory) throws JMSException {
		log.warn("mqconnectionfactory is {}",mqConnectionFactory);
		final JmsPoolConnectionFactory jmsPoolConnectionFactory = new JmsPoolConnectionFactory();
		jmsPoolConnectionFactory.setConnectionFactory(mqConnectionFactory);
		jmsPoolConnectionFactory.setBlockIfSessionPoolIsFull(cloudpropA.getBlockIfFull());
		// jmsPoolConnectionFactory.setBlockIfSessionPoolIsFullTimeout(cloudpropA.getBlockIfFullTimeout());
		// jmsPoolConnectionFactory.setConnectionCheckInterval(cloudpropA.getTimeBetweenExpirationCheck());
		jmsPoolConnectionFactory.setConnectionIdleTimeout(cloudpropA.getIdleTimeout());
		jmsPoolConnectionFactory.setMaxConnections(cloudpropA.getMaxConnections());
		jmsPoolConnectionFactory.setMaxSessionsPerConnection(cloudpropA.getMaxSessionsPerConnection());
		return jmsPoolConnectionFactory;
	}

	@Bean("jmsPoolConnectionFactoryB")
	public javax.jms.ConnectionFactory jmsPoolConnectionFactoryB(final CloudpropB cloudpropB,
			final javax.jms.ConnectionFactory mqConnectionFactory) throws JMSException {
		log.warn("mqconnectionfactory is {}",mqConnectionFactory);
		final JmsPoolConnectionFactory jmsPoolConnectionFactory = new JmsPoolConnectionFactory();
		jmsPoolConnectionFactory.setConnectionFactory(mqConnectionFactory);
		jmsPoolConnectionFactory.setBlockIfSessionPoolIsFull(cloudpropB.getBlockIfFull());
		// jmsPoolConnectionFactory.setBlockIfSessionPoolIsFullTimeout(cloudpropB.getBlockIfFullTimeout());
		// jmsPoolConnectionFactory.setConnectionCheckInterval(cloudpropB.getTimeBetweenExpirationCheck());
		jmsPoolConnectionFactory.setConnectionIdleTimeout(cloudpropB.getIdleTimeout());
		jmsPoolConnectionFactory.setMaxConnections(cloudpropB.getMaxConnections());
		jmsPoolConnectionFactory.setMaxSessionsPerConnection(cloudpropB.getMaxSessionsPerConnection());
		return jmsPoolConnectionFactory;
	}

	@Bean("jmsPoolConnectionFactoryC")
	public javax.jms.ConnectionFactory jmsPoolConnectionFactoryC(final CloudpropC cloudpropC,
			final javax.jms.ConnectionFactory mqConnectionFactory) throws JMSException {
		log.warn("mqconnectionfactory is {}",mqConnectionFactory);
		final JmsPoolConnectionFactory jmsPoolConnectionFactory = new JmsPoolConnectionFactory();
		jmsPoolConnectionFactory.setConnectionFactory(mqConnectionFactory);
		jmsPoolConnectionFactory.setBlockIfSessionPoolIsFull(cloudpropC.getBlockIfFull());
		// jmsPoolConnectionFactory.setBlockIfSessionPoolIsFullTimeout(cloudpropC.getBlockIfFullTimeout());
		// jmsPoolConnectionFactory.setConnectionCheckInterval(cloudpropC.getTimeBetweenExpirationCheck());
		jmsPoolConnectionFactory.setConnectionIdleTimeout(cloudpropC.getIdleTimeout());
		jmsPoolConnectionFactory.setMaxConnections(cloudpropC.getMaxConnections());
		jmsPoolConnectionFactory.setMaxSessionsPerConnection(cloudpropC.getMaxSessionsPerConnection());
		return jmsPoolConnectionFactory;
	}

	@Bean("defaultJmsListenerContainerFactoryA")
	public DefaultJmsListenerContainerFactory defaultJmsListenerContainerFactoryA(
			final javax.jms.ConnectionFactory jmsPoolConnectionFactoryA,
			final DefaultJmsListenerContainerFactoryConfigurer configurer, final CloudpropA cloudpropA,
			final RabbitTransactionManager transactionManager) {
		log.warn("jmsPoolconnectionfactory is {}",jmsPoolConnectionFactoryA);
		final DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
		factory.setMessageConverter(jacksonJmsMessageConverter());
		// factory.setErrorHandler(customErrorHandler);
		factory.setPubSubDomain(Boolean.FALSE);
		factory.setSessionTransacted(true);
		// factory.setSessionAcknowledgeMode(AcknowledgeMode.CLIENT.getMode());
		if (cloudpropA.getExponentialBackoff()) {
			final ExponentialBackOff exponentialBackOff = new ExponentialBackOff(cloudpropA.getReconnectInterval(),
					cloudpropA.getMultiplier());
			exponentialBackOff.setMaxInterval(cloudpropA.getMaxInterval());
			factory.setBackOff(exponentialBackOff);
		} else {
			final FixedBackOff fixedBackOff = new FixedBackOff();
			fixedBackOff.setInterval(cloudpropA.getReconnectInterval());
			// fixedBackOff.setMaxAttempts(3);
			factory.setBackOff(fixedBackOff);
		}
		factory.setTransactionManager(transactionManager);
		configurer.configure(factory, jmsPoolConnectionFactoryA);
		return factory;
	}

	@Bean("defaultJmsListenerContainerFactoryB")
	public DefaultJmsListenerContainerFactory defaultJmsListenerContainerFactoryB(
			@Qualifier("jmsPoolConnectionFactoryB") final javax.jms.ConnectionFactory jmsPoolConnectionFactoryB,
			final DefaultJmsListenerContainerFactoryConfigurer configurer, final CloudpropB cloudpropB,
			final RabbitTransactionManager transactionManager) {
		log.warn("jmsPoolconnectionfactory is {}",jmsPoolConnectionFactoryB);
		final DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
		factory.setMessageConverter(jacksonJmsMessageConverter());
		// factory.setErrorHandler(customErrorHandler);
		factory.setPubSubDomain(Boolean.FALSE);
		factory.setSessionTransacted(true);
		// factory.setSessionAcknowledgeMode(AcknowledgeMode.CLIENT.getMode());
		if (cloudpropB.getExponentialBackoff()) {
			final ExponentialBackOff exponentialBackOff = new ExponentialBackOff(cloudpropB.getReconnectInterval(),
					cloudpropB.getMultiplier());
			exponentialBackOff.setMaxInterval(cloudpropB.getMaxInterval());
			factory.setBackOff(exponentialBackOff);
		} else {
			final FixedBackOff fixedBackOff = new FixedBackOff();
			fixedBackOff.setInterval(cloudpropB.getReconnectInterval());
			// fixedBackOff.setMaxAttempts(3);
			factory.setBackOff(fixedBackOff);
		}
		factory.setTransactionManager(transactionManager);
		configurer.configure(factory, jmsPoolConnectionFactoryB);
		return factory;
	}

	@Bean("defaultJmsListenerContainerFactoryC")
	public DefaultJmsListenerContainerFactory defaultJmsListenerContainerFactoryC(
			@Qualifier("jmsPoolConnectionFactoryC") final javax.jms.ConnectionFactory jmsPoolConnectionFactoryC,
			final DefaultJmsListenerContainerFactoryConfigurer configurer, final CloudpropC cloudpropC,
			final RabbitTransactionManager transactionManager) {
		log.warn("jmsPoolconnectionfactory is {}",jmsPoolConnectionFactoryC);
		final DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
		factory.setMessageConverter(jacksonJmsMessageConverter());
		// factory.setErrorHandler(customErrorHandler);
		factory.setPubSubDomain(Boolean.FALSE);
		factory.setSessionTransacted(true);
		// factory.setSessionAcknowledgeMode(AcknowledgeMode.CLIENT.getMode());
		if (cloudpropC.getExponentialBackoff()) {
			final ExponentialBackOff exponentialBackOff = new ExponentialBackOff(cloudpropC.getReconnectInterval(),
					cloudpropC.getMultiplier());
			exponentialBackOff.setMaxInterval(cloudpropC.getMaxInterval());
			factory.setBackOff(exponentialBackOff);
		} else {
			final FixedBackOff fixedBackOff = new FixedBackOff();
			fixedBackOff.setInterval(cloudpropC.getReconnectInterval());
			// fixedBackOff.setMaxAttempts(3);
			factory.setBackOff(fixedBackOff);
		}
		factory.setTransactionManager(transactionManager);
		configurer.configure(factory, jmsPoolConnectionFactoryC);
		return factory;
	}

	@Bean
	public JmsTemplate jmsTemplate(final javax.jms.ConnectionFactory mqConnectionFactory) throws JMSException {
		log.warn("mq connection factory : {}");
		final JmsTemplate jmsTemplate = new JmsTemplate(mqConnectionFactory);
		jmsTemplate.setMessageConverter(jacksonJmsMessageConverter());
		return jmsTemplate;
	}

	@Bean
	public MappingJackson2MessageConverter jacksonJmsMessageConverter() {
		final MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
		converter.setTargetType(MessageType.TEXT);
		converter.setTypeIdPropertyName("_type");
		return converter;
	}

	@Bean("transactionManager")
	public RabbitTransactionManager transactionManager(final ConnectionFactory connectionFactory) {
		RabbitTransactionManager rabbitTransactionManager = new RabbitTransactionManager(connectionFactory);
		return rabbitTransactionManager;
	}

}
