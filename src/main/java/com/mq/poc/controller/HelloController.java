package com.mq.poc.controller;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mq.poc.util.AppConstant;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class HelloController {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@GetMapping("mq/status")
	public String healthCheck() {
		log.info("inside healthCheck");
		return AppConstant.OK;
	}

	@GetMapping("mq/send")
	public String sendMessage() {
		try {
			rabbitTemplate.convertAndSend("exchangeA", "ibm.mq.route.A", "hello message");
		} catch (Exception exception) {
			throw new RuntimeException("exception occured in rabbit mq controller");
		}
		return "sent";
	}
}
