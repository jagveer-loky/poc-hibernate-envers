package com.fiserv.preproposal.api.application.exceptions;


public class MandatoryFieldException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public MandatoryFieldException(String message) {
		super(message);
	}

	public MandatoryFieldException(Throwable cause) {
		super(cause);
	}
}
