package com.fiserv.preproposalApi.application.properties;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter 
public class CorsProperties {
	@Value("${cors.enabled}")
	private boolean enabled;

	@Value("${cors.allowed-methods}")
	private String[] allowedMethods = new String[0];

	@Value("${cors.allowed-headers}")
	private String[] allowedHeaders = new String[0];

	@Value("${cors.exposed-headers}")
	private String[] exposedHeaders = new String[0];

	@Value("${cors.allowed-origins}")
	private String[] allowedOrigins = new String[0];
}
