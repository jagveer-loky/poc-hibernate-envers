package com.fiserv.luc.api.application.config.security.filter;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiserv.luc.api.application.aspect.exceptions.FDSecurityException;
import com.fiserv.luc.api.application.config.security.fd.DSecurity;
import com.fiserv.luc.api.application.config.security.fd.FDSecurity;
import com.fiserv.luc.api.application.config.security.fd.FDSecurityEnum;
import com.fiserv.luc.api.infrastructure.aid.enums.ApplicationEnum;
import com.fiserv.luc.api.infrastructure.aid.enums.EventActivityEnum;
import com.fiserv.luc.api.infrastructure.aid.util.LogUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.Base64;
import java.util.Collection;
import java.util.logging.Logger;

@RequiredArgsConstructor
public class AuthorizationFilter extends GenericFilterBean {

    private static final Logger LOGGER = Logger.getLogger(AuthorizationFilter.class.getName());

    @Autowired
    private ObjectMapper objectMapper;

    final FDSecurity fdSecurity;
    final HandlerExceptionResolver resolver;

    @SuppressWarnings("unused")
    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException, ServletException {

        final HttpServletRequest req = (HttpServletRequest) request;
        final HttpServletResponse respo = (HttpServletResponse) response;

        // Setting Security Context to get this information in EntityTrackingRevisionListener by SecurityContextHolder singleton
        final SecurityContext secContext = new SecurityContextImpl(new UsernamePasswordAuthenticationToken(extractUserDetailsFromRequest(req), null, null));
        SecurityContextHolder.setContext(secContext);

        LOGGER.info(LogUtil.buildMessage(ApplicationEnum.SBA, req, EventActivityEnum.AUDIT_REQUEST, "Starting request for :" + req.getRequestURI()));
        DSecurity fdsecurityResp = null;

        try {
            //O APIGEE fará a validação e enviarra este atributo no header identificando que a API já foi validada
            if (req.getHeader("x-security-check") == null || !Boolean.parseBoolean(req.getHeader("x-security-check"))) {
                fdSecurity.canAccessUser(req, FDSecurityEnum.TOKEN);
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

        LOGGER.info(LogUtil.buildMessage(ApplicationEnum.SBA, req, EventActivityEnum.AUDIT_REQUEST, "Committing request for :" + req.getRequestURI()));
    }

    @Override
    public void destroy() {
    }

    /**
     * @param request ServletRequest
     * @return User
     * @throws JsonProcessingException
     */
    private UserDetails extractUserDetailsFromRequest(final ServletRequest request) throws JsonProcessingException {
        final String token = extractTokenFromRequest(request);
        final String jsonString = extractJsonStringFromToken(token);
        return extractUserDetailsFromJsonString(jsonString);
    }

    /**
     * @param token String
     * @return String
     */
    private static String extractJsonStringFromToken(final String token) {

        final String[] chunks = token.split("\\.");

        final Base64.Decoder decoder = Base64.getDecoder();

        return new String(decoder.decode(chunks[1]));
    }

    /**
     * @param request ServletRequest
     * @return String
     */
    public static String extractTokenFromRequest(final ServletRequest request) {

        final HttpServletRequest httpRequest = (HttpServletRequest) request;

        return httpRequest.getHeader("authorization");
    }

    /**
     * @param jsonString String
     * @return User
     */
    private UserDetails extractUserDetailsFromJsonString(final String jsonString) throws JsonProcessingException {
        return objectMapper.readValue(jsonString, User.class);
    }


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class User implements UserDetails, Serializable {
        private String password = "PASSWORD";
        private String email;

        public User(final String email) {
            this.email = email;
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return null; //TODOs
        }

        @Override
        public String getUsername() {
            return email;
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return true;
        }
    }

}