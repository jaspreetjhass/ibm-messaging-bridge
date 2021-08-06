package com.mq.poc.rabbit.listener;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class RabbitListenerA {

	//@RabbitListener(queues = "queueA", containerFactory = "simpleRabbitListenerContainerFactory", concurrency = "${mq-bridge.rabbit.listener.concurrency}", autoStartup = "${mq-bridge.rabbit.listener.auto-startup}")
	public void onReceive(Message message) {
		byte[] body = message.getBody();
		log.info("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% Message Received by Rabbit listener A is : {}",
				new String(body));
	}

}
