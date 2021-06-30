package com.fiserv.preproposal.api.application.security.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fiserv.preproposal.api.application.security.FDSecurity;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.servlet.HandlerExceptionResolver;

import com.fiserv.preproposal.api.application.exceptions.FDSecurityException;
import com.fiserv.preproposal.api.application.security.FDSecurityEnum;
import com.fiserv.preproposal.api.infrastrucutre.aid.enums.ApplicationEnum;
import com.fiserv.preproposal.api.infrastrucutre.aid.enums.EventActivityEnum;
import com.fiserv.preproposal.api.application.pagination.DSecurity;
import com.fiserv.preproposal.api.infrastrucutre.aid.LogUtil;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AuthorizationFilter extends GenericFilterBean {

	private static final Logger LOGGER = LogManager.getLogger();
	
	FDSecurity fdSecurity;
	HandlerExceptionResolver resolver;

	@SuppressWarnings("unused")
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse respo = (HttpServletResponse) response;

		LOGGER.info(LogUtil.buildMessage(ApplicationEnum.SBA, req, EventActivityEnum.AUDIT_REQUEST, "Starting request for :" + req.getRequestURI()));
		DSecurity fdsecurityResp = null;
		
		try {
			//O APIGEE fará a validação e enviarra este atributo no header identificando que a API já foi validada 
			if (req.getHeader("x-security-check") == null || !Boolean.valueOf(req.getHeader("x-security-check"))) {
				fdsecurityResp = fdSecurity.canAccessUser(req, FDSecurityEnum.TOKEN);
			}
			
			chain.doFilter(request, response);
			
		} catch (FDSecurityException e) {
			LOGGER.info(LogUtil.buildMessage(ApplicationEnum.SBA, req, EventActivityEnum.AUDIT_REQUEST, "FDSecurity Exception"));
			LOGGER.info(ExceptionUtils.getStackTrace(e));
			resolver.resolveException(req, respo, null, e);
		} catch (Exception e) {
			LOGGER.info(LogUtil.buildMessage(ApplicationEnum.SBA, req, EventActivityEnum.AUDIT_REQUEST, "Exception"));
			LOGGER.info(ExceptionUtils.getStackTrace(e));
			resolver.resolveException(req, respo, null, e);
		}

		LOGGER.info(LogUtil.buildMessage(ApplicationEnum.SBA, req, EventActivityEnum.AUDIT_REQUEST,	"Committing request for :" + req.getRequestURI()));
	}

	@Override
	public void destroy() {
	}

}