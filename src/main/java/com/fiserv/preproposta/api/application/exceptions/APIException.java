package com.fiserv.preproposta.api.application.exceptions;


public class APIException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public APIException(String message) {
		super(message);
	}

	public APIException(Throwable cause) {
		super(cause);
	}
}
