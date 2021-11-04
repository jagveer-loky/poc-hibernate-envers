package com.fiserv.preproposta.api.infrastrucutre.aid.util;

import java.util.Locale;

import com.fiserv.preproposta.api.infrastrucutre.aid.enums.ResponsesAndExceptionEnum;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.fiserv.preproposta.api.domain.dtos.MessageSourceDTO;

@Component
public class MessageSourceUtil {

	private static MessageSource messageSource;

	public MessageSourceUtil(MessageSource messageSource) {
		MessageSourceUtil.messageSource = messageSource;
	}
	
	public static MessageSourceDTO getProperties(ResponsesAndExceptionEnum value) {
		MessageSourceDTO message = new MessageSourceDTO();
		message.setCode(String.valueOf(value.getCode()));
		message.setMessage(messageSource.getMessage(value.getMessageKey(), null, Locale.getDefault()));
		return message;
	}
	
	public static MessageSourceDTO getProperties(ResponsesAndExceptionEnum value, String... params) {
		MessageSourceDTO message = new MessageSourceDTO();
		message.setCode(String.valueOf(value.getCode()));
		message.setMessage(messageSource.getMessage(value.getMessageKey(), params, Locale.getDefault()));
		return message;
	}
}
