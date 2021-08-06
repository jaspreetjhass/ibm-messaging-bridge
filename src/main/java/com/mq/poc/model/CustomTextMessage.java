package com.mq.poc.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomTextMessage implements Serializable {

	private static final long serialVersionUID = -4391478316834394400L;

	private Long id;
	private String message;

}
