package com.fiserv.luc.api.application.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.OAuthFlow;
import io.swagger.v3.oas.models.security.OAuthFlows;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Configuration
public class SwaggerConfig {

    public static final String SECURITY_SCHEME = "JWT";

    @Value("${application.version}")
    private String apiVersion;
    
    @Value("${application.name}")
    private String apiName;
    
    @Value("${application.description}")
    private String apiDescription;

    @Bean
    public OpenAPI api() {
        return new OpenAPI()
            .info(apiInfo())
            .components(addComponents())
            .addSecurityItem(new SecurityRequirement().addList(SECURITY_SCHEME))
            ;
    }

    private Components addComponents() {
        return new Components().addSecuritySchemes(SECURITY_SCHEME, new SecurityScheme()
            .type(SecurityScheme.Type.OAUTH2)
            .in(SecurityScheme.In.HEADER)
            .bearerFormat("JWT")
            .flows(new OAuthFlows()
                .password(new OAuthFlow()
                    .tokenUrl("/v1/token")
                    .refreshUrl("/v1/token")
                )
            )
            .name(AUTHORIZATION))
            ;
    }

    private Info apiInfo() {
        return new Info()
            .title(apiName)
            .description(apiDescription)
            .version(apiVersion);
    }
}