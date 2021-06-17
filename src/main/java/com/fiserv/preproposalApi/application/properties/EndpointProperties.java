package com.fiserv.preproposalApi.application.properties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("endpoints")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EndpointProperties {
    private boolean debug;

    private API api1;

    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class API {
        @Value("${base-url}")
        private String baseUrl;

        private String username;

        private String password;
    }
}
