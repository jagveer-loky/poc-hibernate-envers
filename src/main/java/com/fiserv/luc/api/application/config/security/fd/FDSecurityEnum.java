package com.fiserv.luc.api.application.config.security.fd;

public enum FDSecurityEnum {

	CLIENT 				("/merchant/{instituicao}/{merchant}"),
	DOCUMENT 			("/document/{instituicao}/{documento}"),
	SERVICE_CONTRACT	("/service-contract/{instituicao}/{serviceContract}"),
	INSTITUTION 		("/instituicao/{instituicao}"),
	TOKEN				(""),
	NONE				("");

	private String endpoint;

	FDSecurityEnum (String endpoint){
		this.endpoint = endpoint;
	}

	public String getEndpoint() {
		return endpoint;
	}

	public static String getDescricao(FDSecurityEnum security) {
		for (FDSecurityEnum sec : FDSecurityEnum.values()) {
			if(sec.equals(security)) {
				return sec.name();
			}
		}
		return NONE.name();
	}

}
