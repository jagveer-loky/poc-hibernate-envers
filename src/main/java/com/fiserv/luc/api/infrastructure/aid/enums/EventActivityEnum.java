package com.fiserv.luc.api.infrastructure.aid.enums;


public enum EventActivityEnum {
	APPLICATION_AUTHENTICATION_SUCCESS("Application Authentication", "Application Authentication Success"),
	APPLICATION_AUTHENTICATION_FAILURE("Application Authentication", "Application Authentication Failure"),

	AUDIT_REQUEST("Data Audit Trails", "Normal data access"),
	AUDIT_SELECT("Data Audit Trails", "Sensitive data access - select - sensitive data element name and value"),
	AUDIT_ADD("Data Audit Trails", "Sensitive data access - add - sensitive data element name and value"),
	AUDIT_UPDATE("Data Audit Trails", "Sensitive data access - update - sensitive data element name and updated value"),
	AUDIT_DELETE("Data Audit Trails", "Sensitive data access - delete - sensitive data element name and deleted value"),

	OUTPUT_VALIDATION_DATABASE("Output Validation failures", "Database record mismatch"),
	OUTPUT_VALIDATION_ENCODING("Output Validation failures", "Invalid data encoding"),

	SUSPISCIOUS("Suspicious Behavior", "Suspicious Behaviour - Suspicious activity as defined by IT Security & Risk management SME´S"),

	SESSION_MANAGEMENT_CREATION("Session Management Failures", "Cookie creation failed"),
	SESSION_MANAGEMENT_IDENTIFICATION("Session Management Failures", "Cookie session identification"),
	SESSION_MANAGEMENT_CHANGE("Session Management Failures", "Value modified in a session - Session field name and value"),

	APPLICATION_SYNTAX_ERROR("Applications Erros and Events", "Syntax Error"),
	APPLICATION_RUNTIME_ERROR("Applications Erros and Events", "Runtime Error"),
	APPLICATION_CONECTIVITY_ISSUE("Applications Erros and Events", "Conectivity Issue"),
	APPLICATION_THIRD_PARTY_ERROR("Applications Erros and Events", "Thurd party Service Error"),
	APPLICATION_FILE_SYSTEM_ERROR("Applications Erros and Events", "File System Error"),
	APPLICATION_SEQUENCING_FAILURE("Applications Erros and Events", "Sequencing Failure"),

	HIGH_RISK_ADDITION("High Risk Functionality", "User addition"),
	HIGH_RISK_DELETION("High Risk Functionality", "User deletion"),
	HIGH_RISK_UPDATE("High Risk Functionality", ""),
	HIGH_RISK_ACCESS_CHANGE("High Risk Functionality", "User access changes"),
	HIGH_RISK_ADMIN_ACCESS("High Risk Functionality", "Admin User access"),
	HIGH_RISK_SENSITIVE_DATA("High Risk Functionality", "Sensitive Data access");


	private final String event;

	private final String action;


	private EventActivityEnum(final String event, final String action) {
		this.event = event;
		this.action = action;
	}

	public String getEvent() {
		return event;
	}

	public String getAction() {
		return action;
	}


}
