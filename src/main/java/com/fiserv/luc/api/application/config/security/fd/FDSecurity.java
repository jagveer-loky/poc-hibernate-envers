package com.fiserv.luc.api.application.config.security.fd;

import com.fiserv.luc.api.application.aspect.exceptions.FDSecurityException;
import com.fiserv.luc.api.application.aspect.exceptions.UnauthorizedException;
import com.fiserv.luc.api.infrastructure.aid.enums.ResponsesAndExceptionEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpServerErrorException;

import javax.servlet.http.HttpServletRequest;

@Component
public class FDSecurity {

	@Autowired
	FDSecurityClient security;

	public DSecurity canAccessUser(HttpServletRequest request, FDSecurityEnum securityEnum) {
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", request.getHeader("Authorization"));
		MessageSourceDTO properties = MessageSourceUtil.getProperties(ResponsesAndExceptionEnum.UNAUTHORIZED_EXCEPTION);
		ResponseEntity<DSecurity> response = new ResponseEntity<DSecurity>(
												new DSecurity(Integer.parseInt(properties.getCode()), 
															  properties.getMessage()), HttpStatus.UNAUTHORIZED);
		try {
			
			switch (FDSecurityEnum.getDescricao(securityEnum)) {

			case "CLIENT":
				response = security.findPermissionClient(FDSecurityEnum.CLIENT, request.getHeader("Authorization"),
						!StringUtils.isEmpty(request.getHeader("instituicao")) ? request.getHeader("instituicao"): request.getHeader("institution") ,
								!StringUtils.isEmpty(request.getHeader("estabelecimento")) ? request.getHeader("estabelecimento") : request.getHeader("merchant") );
				break;

			case "DOCUMENT":
				response = security.findPermissionByCpfCnpj(FDSecurityEnum.DOCUMENT, request.getHeader("Authorization"),
						!StringUtils.isEmpty(request.getHeader("instituicao")) ? request.getHeader("instituicao"): request.getHeader("institution") ,
								!StringUtils.isEmpty(request.getHeader("documento")) ? request.getHeader("documento"): request.getHeader("document") );
				break;

			case "SERVICE_CONTRACT":
				response = security.findPermissionByServiceContract(FDSecurityEnum.SERVICE_CONTRACT, request.getHeader("Authorization"),
						!StringUtils.isEmpty(request.getHeader("instituicao")) ? request.getHeader("instituicao"): request.getHeader("institution") ,
								!StringUtils.isEmpty(request.getHeader("service-contract")) ? request.getHeader("service-contract"): request.getHeader("codigo-contrato") );
				break;

			case "INSTITUTION":
				response = security.findPermissionInstitution(FDSecurityEnum.INSTITUTION, request.getHeader("Authorization"),
						!StringUtils.isEmpty(request.getHeader("instituicao")) ? request.getHeader("instituicao"): request.getHeader("institution") );
				break;

			case "TOKEN":
				response = security.findPermission(FDSecurityEnum.TOKEN, request.getHeader("Authorization"));
				break;

			case "NONE":
				throw new FDSecurityException();

			default:
				throw new FDSecurityException();

			}
			
		} catch (FDSecurityException e)	{
			throw e;	
		} catch (HttpServerErrorException e) {
			throw new UnauthorizedException();
		} catch (Exception e) {
			throw new UnauthorizedException();
		}
		
		return response.getBody();
	}
}



