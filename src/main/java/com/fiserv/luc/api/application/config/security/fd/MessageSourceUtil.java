package com.fiserv.luc.api.application.config.security.fd;

import com.fiserv.luc.api.infrastructure.aid.enums.ResponsesAndExceptionEnum;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Locale;

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

    public static String cropMessage(final String message, final int index) {
        Assert.isTrue(index > 0, "The index must be greater than 0");
        Assert.notNull(message, "The message cannot be null");
        return message.length() > 254 ? message.substring(0, 253) : message;
    }
}
