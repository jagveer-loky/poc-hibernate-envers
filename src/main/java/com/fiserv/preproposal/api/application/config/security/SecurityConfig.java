package com.fiserv.preproposal.api.application.config.security;

import com.fiserv.preproposal.api.application.config.security.filter.AuthorizationFilter;
import com.fiserv.preproposal.api.application.properties.CorsProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.util.Arrays;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private FDSecurity fdSecurity;

    private final CorsProperties corsProperties;

    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver resolver;

    public SecurityConfig(CorsProperties corsProperties) {
        this.corsProperties = corsProperties;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.headers().contentSecurityPolicy(getCSP());
        http.csrf().disable().headers().frameOptions().sameOrigin();

        if (corsProperties.isEnabled())
            http.cors().and().authorizeRequests(r -> r.requestMatchers(CorsUtils::isPreFlightRequest).permitAll());

        http.authorizeRequests(r ->
//                r.antMatchers("/v3/api-docs/**", "/v3/api-docs.yaml**", "/swagger-ui/**", "/swagger-ui.html**", "/actuator/**").permitAll()
//                        .antMatchers("/v*/token/**").anonymous()
//                        .antMatchers("/isAlive**").permitAll()
//                        .antMatchers("/v*/**").permitAll()
//                        .anyRequest().permitAll().and().addFilterBefore(new AuthorizationFilter(fdSecurity, resolver), UsernamePasswordAuthenticationFilter.class))
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        r.antMatchers("/**").permitAll());
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().mvcMatchers("/v3/api-docs/**", "/v3/api-docs.yaml**", "/swagger-ui/**", "/swagger-ui.html**", "/actuator/**");
        web.ignoring().mvcMatchers("/isAlive**");
    }

    @Bean
    @ConditionalOnProperty(value = "cors.enabled", havingValue = "true")
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedMethods(Arrays.asList(corsProperties.getAllowedMethods()));
        config.setAllowedHeaders(Arrays.asList(corsProperties.getExposedHeaders()));
        config.setExposedHeaders(Arrays.asList(corsProperties.getExposedHeaders()));
        config.setAllowedOrigins(Arrays.asList(corsProperties.getAllowedOrigins()));

        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }

    private String getCSP() {
        return "default-src 'self'";
    }

}
