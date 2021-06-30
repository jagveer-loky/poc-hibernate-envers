package com.fiserv.preproposal.api.application.config.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fiserv.preproposal.api.application.pagination.DSecurity;

@Component
public class FDSecurityClient {
	
	@Value("${fdsecurity.url}")
	private String url;
		
	private ResponseEntity<DSecurity> serviceRest(String endpoint, String token) {
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<DSecurity>  response;
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", token);
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> requestEntity = new HttpEntity<>(headers);
		response = restTemplate.exchange(url + endpoint, HttpMethod.GET, requestEntity, DSecurity.class);
		return response;
	}
	
	public ResponseEntity<DSecurity> findPermissionClient (FDSecurityEnum securityEnum, String token, String instituicao, String merchant) {
		String endpoint = securityEnum.getEndpoint().replace("{instituicao}", instituicao);
		endpoint = endpoint.replace("{merchant}", merchant);
		return serviceRest(endpoint, token);
	}

	public ResponseEntity<DSecurity> findPermissionByCpfCnpj (FDSecurityEnum securityEnum, String token, String instituicao, String documento){
		String endpoint = securityEnum.getEndpoint().replace("{instituicao}", instituicao);
		endpoint = endpoint.replace("{documento}", documento);
		return serviceRest(endpoint, token);
	}
	
	public ResponseEntity<DSecurity> findPermissionByServiceContract (FDSecurityEnum securityEnum, String token, String instituicao, String serviceContract){
		String endpoint = securityEnum.getEndpoint().replace("{instituicao}", instituicao);
		endpoint = endpoint.replace("{serviceContract}", serviceContract);
		return serviceRest(endpoint, token);
	}
	
	public ResponseEntity<DSecurity> findPermissionInstitution (FDSecurityEnum securityEnum, String token, String instituicao){
		String endpoint = securityEnum.getEndpoint().replace("{instituicao}", instituicao);
		return serviceRest(endpoint, token);
	}
	
	public ResponseEntity<DSecurity> findPermission (FDSecurityEnum securityEnum, String token) {
		return serviceRest("", token);
	}

}
