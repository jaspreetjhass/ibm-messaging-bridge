package com.mq.poc.config;

import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class RabbitConfig {

	@Bean
	public SimpleRabbitListenerContainerFactory simpleRabbitListenerContainerFactory(
			final CachingConnectionFactory cachingConnectionFactory) {
		final SimpleRabbitListenerContainerFactory simpleRabbitListenerContainerFactory = new SimpleRabbitListenerContainerFactory();
		simpleRabbitListenerContainerFactory.setConnectionFactory(cachingConnectionFactory);
		return simpleRabbitListenerContainerFactory;
	}

	@Bean
	public RabbitTemplate rabbitTemplate(final CachingConnectionFactory cachingConnectionFactory) {
		final RabbitTemplate rabbitTemplate = new RabbitTemplate(cachingConnectionFactory);
		rabbitTemplate.setChannelTransacted(true);
		rabbitTemplate.setMessageConverter(jackson2JsonMessageConverter());
		return rabbitTemplate;
	}
	
	@Bean
	public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
		Jackson2JsonMessageConverter jackson2JsonMessageConverter = new Jackson2JsonMessageConverter();
		//jackson2JsonMessageConverter.setClassMapper(classMapper());
		return jackson2JsonMessageConverter;
	}

}
