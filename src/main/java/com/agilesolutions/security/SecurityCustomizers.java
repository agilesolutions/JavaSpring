package com.agilesolutions.security;

import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

import static org.springframework.http.HttpHeaders.*;

public class SecurityCustomizers {

    public static final Customizer<CsrfConfigurer<HttpSecurity>> csrfCustomizer = csrf -> csrf.disable();

    public static final Customizer<CorsConfigurer<HttpSecurity>> corsCustomizer =
            corsConfig -> corsConfig.configurationSource(request -> {
                var cors = new CorsConfiguration();

                cors.setAllowedMethods(List.of("GET", "OPTIONS", "POST", "PUT", "DELETE", "OPTIONS"));
                cors.setAllowedHeaders(List.of(
                        ORIGIN
                        , ACCESS_CONTROL_ALLOW_ORIGIN
                        , CONTENT_TYPE
                        ,ACCEPT
                ));
                cors.setExposedHeaders(List.of(
                        ORIGIN
                        , ACCESS_CONTROL_ALLOW_ORIGIN
                        , CONTENT_TYPE
                        ,ACCEPT
                ));
                cors.setAllowCredentials(true);

                return cors;

            });

    public static final Customizer<HeadersConfigurer<HttpSecurity>.ContentSecurityPolicyConfig> cspCustomizer = csp -> {
        String all = String.join(";", List.of(
                "default-src 'self'",
                "script-src 'self'"
        ));

        csp.policyDirectives(all);
    };


    public static final Customizer<HeadersConfigurer<HttpSecurity>> headersCustomizer = headers ->
            headers.xssProtection(HeadersConfigurer.XXssConfig::disable)
                    .contentSecurityPolicy(cspCustomizer)
                    .httpStrictTransportSecurity(hstsConfig -> hstsConfig.maxAgeInSeconds(31536000).includeSubDomains(true).preload(false));


}
