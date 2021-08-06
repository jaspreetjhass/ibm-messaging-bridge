/**
 * 
 */
package com.mq.poc.mq.listener;

import javax.jms.JMSException;
import javax.jms.Message;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mq.poc.model.CustomTextMessage;
import com.mq.poc.util.AppConstant;

import lombok.extern.slf4j.Slf4j;

/**
 * @author jaspreet.singh
 *
 */
@Component
@Slf4j
public class ListenerA {

	@Autowired
	private RabbitTemplate rabbitTemplate;
	@Autowired
	private ObjectMapper objectMapper;

	@Transactional(transactionManager = AppConstant.TRANSACTION_MANAGER)
	@JmsListener(destination = AppConstant.QUEUEA, containerFactory = AppConstant.CONNECTION_FACTORY_A, concurrency = "${mq.a.ibm.mq.pool.concurrency}")
	public void receiveMessage(Message message) throws JMSException, JsonMappingException, JsonProcessingException {
		log.info("enter into receiveMessage method with parameters : {} ", message);
		CustomTextMessage customTextMessage = objectMapper.readValue(message.getBody(String.class),
				CustomTextMessage.class);
		rabbitTemplate.convertAndSend(AppConstant.EXCHANGE_A, AppConstant.ROUTE_KEY_A, customTextMessage);
		log.info("exit from receiveMessage method");
	}
}
