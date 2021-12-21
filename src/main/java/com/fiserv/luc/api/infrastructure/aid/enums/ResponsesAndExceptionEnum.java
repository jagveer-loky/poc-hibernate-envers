package com.fiserv.luc.api.infrastructure.aid.enums;

public enum ResponsesAndExceptionEnum {

	// O range 1 -> 100 reservado para excecoes padrao da api

	EXCEPTION							(  2, "error.server"			),
	ACCESS_DENIED_EXCEPTION 			(  3, "error.accessdenied"		),
	UNAUTHORIZED_EXCEPTION				(  4, "error.unauthorized"		),
	ITEM_NOT_FOUND_EXCEPTION			(  5, "error.notfound"			),
	INVALID_TOKEN_KEYCLOAK_EXCEPTION	(  6, "error.invalidtoken"		),
	API_EXCEPTION						(  7, "error.api"				),
	DUPLICATED_EXCEPTION				(  8, "error.duplicated"		),
	MANDATORY_FIELD_EXCEPTION			(  9, "error.mandatoryfield"	),
	ERRO_VALIDATION						( 10, "error.validation"		),
	FDSECURITY_EXCEPTION				( 11, "error.fdsecurity"		),
	INCOMPLETE_FILTER                   ( 12, "error.filter"            ),
	NSA_NOT_FOUND                       ( 13, "error.nsa.notfound"      ),
    FILE_NOT_FOUND                      ( 14, "error.file.notfound"      ),
	// O range a partir do 101 -> 300 destinado a exeções especificas da api




	// O range a partir do 301 destinado a retornos da api
	CONSULTA_REALIZADA					(301, "response.consultarealizada"),

	;

	private final Integer code;
	private final String messageKey;

	private ResponsesAndExceptionEnum (final Integer code, final String messageKey) {
		this.code = code;
		this.messageKey = messageKey;
	}

	public Integer getCode() {
		return code;
	}

	public String getMessageKey() {
		return messageKey;
	}


}
