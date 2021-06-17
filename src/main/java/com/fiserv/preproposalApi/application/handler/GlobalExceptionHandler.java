package com.fiserv.preproposalApi.application.handler;

import com.fiserv.preproposalApi.application.exceptions.FilterException;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.MessageSource;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fiserv.preproposalApi.infrastrucutre.aid.enums.ApplicationEnum;
import com.fiserv.preproposalApi.infrastrucutre.aid.enums.EventActivityEnum;
import com.fiserv.preproposalApi.infrastrucutre.aid.enums.ResponsesAndExceptionEnum;
import com.fiserv.preproposalApi.application.exceptions.APIException;
import com.fiserv.preproposalApi.application.exceptions.DuplicatedException;
import com.fiserv.preproposalApi.application.exceptions.InvalidTokenKeycloakException;
import com.fiserv.preproposalApi.application.exceptions.ItemNotFoundException;
import com.fiserv.preproposalApi.application.exceptions.MandatoryFieldException;
import com.fiserv.preproposalApi.application.exceptions.UnauthorizedException;
import com.fiserv.preproposalApi.application.exceptions.FDSecurityException;
import com.fiserv.preproposalApi.application.pagination.DResponse;
import com.fiserv.preproposalApi.infrastrucutre.aid.LogUtil;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
	
	private static final Logger LOGGER = LogManager.getLogger();

	private final MessageSource messageSource;

	public GlobalExceptionHandler(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@ExceptionHandler(Exception.class)
	private ResponseEntity<Object> handleGeneral(Exception e, WebRequest request) {
		LOGGER.info(LogUtil.buildMessage(ApplicationEnum.SBA, request, "Erro interno"));
		return handleException(ResponsesAndExceptionEnum.EXCEPTION, new Object[] { e.getMessage() }, e, request,	HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(AccessDeniedException.class)
	private ResponseEntity<Object> handleAccessDenied(AccessDeniedException e, WebRequest request) {
		LOGGER.info(LogUtil.buildMessage(ApplicationEnum.SBA, request, "Access denied..."));
		return handleException(ResponsesAndExceptionEnum.ACCESS_DENIED_EXCEPTION, new Object[] { e.getMessage() }, e, request, HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler(UnauthorizedException.class)
	private ResponseEntity<Object> handleUnauthorized(UnauthorizedException e, WebRequest request) {
		LOGGER.info(LogUtil.buildMessage(ApplicationEnum.SBA, request, EventActivityEnum.APPLICATION_AUTHENTICATION_FAILURE, "Unauthorized..."));
		return handleException(ResponsesAndExceptionEnum.UNAUTHORIZED_EXCEPTION, e, request, HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(FDSecurityException.class)
	private ResponseEntity<Object> handleFDSecurity(FDSecurityException e, WebRequest request) {
		LOGGER.info(LogUtil.buildMessage(ApplicationEnum.SBA, request, EventActivityEnum.APPLICATION_AUTHENTICATION_FAILURE, "Unauthorized..."));
		return handleException(ResponsesAndExceptionEnum.FDSECURITY_EXCEPTION, e, request, HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(ItemNotFoundException.class)
	private ResponseEntity<Object> handleItemNotFound(ItemNotFoundException e, WebRequest request) {
		LOGGER.info(LogUtil.buildMessage(ApplicationEnum.SBA, request, "Item not found..."));
		return handleException(ResponsesAndExceptionEnum.ITEM_NOT_FOUND_EXCEPTION, e, request, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(InvalidTokenKeycloakException.class)
	private ResponseEntity<Object> handleInvalidToken(InvalidTokenKeycloakException e, WebRequest request) {
		LOGGER.info(LogUtil.buildMessage(ApplicationEnum.SBA, request, EventActivityEnum.APPLICATION_AUTHENTICATION_FAILURE, "Authentication failed..."));
		return handleException(ResponsesAndExceptionEnum.INVALID_TOKEN_KEYCLOAK_EXCEPTION, e, request, HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler(APIException.class)
	private ResponseEntity<Object> handleAPI(APIException e, WebRequest request) {
		LOGGER.info(LogUtil.buildMessage(ApplicationEnum.SBA,  request,	EventActivityEnum.APPLICATION_RUNTIME_ERROR, "Erro Interno"));
		return handleException(ResponsesAndExceptionEnum.API_EXCEPTION, new Object[] { e.getMessage() }, e, request, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(DuplicatedException.class)
	private ResponseEntity<Object> handleDuplicatedItem(DuplicatedException e, WebRequest request) {
		LOGGER.info(LogUtil.buildMessage(ApplicationEnum.SBA, request, "Duplicated Exception"));
		return handleException(ResponsesAndExceptionEnum.DUPLICATED_EXCEPTION, new Object[] { e.getMessage() }, e, request, HttpStatus.CONFLICT);
	}

	@ExceptionHandler(MandatoryFieldException.class)
	private ResponseEntity<Object> handleMandatoryField(MandatoryFieldException e, WebRequest request) {
		LOGGER.info(LogUtil.buildMessage(ApplicationEnum.SBA, request, "Mandatory Fields."));
		return handleException(ResponsesAndExceptionEnum.MANDATORY_FIELD_EXCEPTION, new Object[] { e.getMessage() }, e, request, HttpStatus.PARTIAL_CONTENT);
	}

	@ExceptionHandler(FilterException.class)
	private ResponseEntity<Object> filterException(FilterException e, WebRequest request) {
		LOGGER.info(LogUtil.buildMessage(ApplicationEnum.SBA, request, "Incomplete filter."));
		return handleException(ResponsesAndExceptionEnum.INCOMPLETE_FILTER, new Object[] { e.getMessage() }, e, request, HttpStatus.BAD_REQUEST);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e, HttpHeaders headers, HttpStatus status, WebRequest request) {
		 List<String> errors = e.getBindingResult()
				                .getFieldErrors()
				                .stream()
				                .map(x -> x.getDefaultMessage())
				                .collect(Collectors.toList());
		return handleException(ResponsesAndExceptionEnum.ERRO_VALIDATION, new Object[] { errors }, e, request, HttpStatus.PARTIAL_CONTENT);
	}
	
    private ResponseEntity<Object> handleException(ResponsesAndExceptionEnum exceptionEnum, Exception e, WebRequest request, HttpStatus status) {
        LOGGER.info(LogUtil.buildMessage(ApplicationEnum.SBA, request, e.getMessage()));
        return handleException(exceptionEnum.getCode(), exceptionEnum.getMessageKey(), new Object[] {}, e, request, status);
    }
	
	private ResponseEntity<Object> handleException(ResponsesAndExceptionEnum exceptionEnum, Object[] params, Exception e, WebRequest request, HttpStatus status) {
		return handleException(exceptionEnum.getCode(), exceptionEnum.getMessageKey(), params, e, request, status);
	}
	
	private ResponseEntity<Object> handleException(Integer codigo, String messageKey, Object[] params, Exception e, WebRequest request, HttpStatus status) {
		LOGGER.info(ExceptionUtils.getStackTrace(e));
		String message = messageSource.getMessage(messageKey, params, Locale.getDefault());
		
		ApiError apiError = new ApiError(status);
		apiError.setMessage(message);
		if(null != codigo) {
			apiError.setCodigo(codigo);
		}
		
		return buildResponseEntity(apiError);
	}

	private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
		return new ResponseEntity<>(DResponse.notOk(apiError.getCodigo(), apiError.getMessage()), apiError.getStatus());
	}
	
}
