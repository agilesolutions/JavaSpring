package com.agilesolutions.security;

import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.gen.RSAKeyGenerator;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.*;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
@AllArgsConstructor
public class WebSecurity {

    private static final String[] WHITLIST = {"/healthCheck","actuator", "/swagger-ui.html"};

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .cors(c -> c.disable())
                .csrf(c -> c.disable())
                .authorizeHttpRequests(a ->
                        a.requestMatchers(
                                        new AntPathRequestMatcher("/login"),
                                        new AntPathRequestMatcher("/oauth2/**"),
                                        new AntPathRequestMatcher("/openid-connect"),
                                        new AntPathRequestMatcher("/error"),
                                        new AntPathRequestMatcher("/css/**"),
                                        new AntPathRequestMatcher("/js/**"),
                                        new AntPathRequestMatcher("/images/**"),
                                        new AntPathRequestMatcher("/actuator/**"),
                                        new AntPathRequestMatcher("/v3/api-docs/**"),
                                        new AntPathRequestMatcher("/swagger-ui/**"),
                                        new AntPathRequestMatcher("/oauth-client/**"),
                                        new AntPathRequestMatcher("/assets/**"))
                                .permitAll()
                                .anyRequest().authenticated())
                .oauth2Login(customizer -> customizer.successHandler(successHandler()))
                .build();
    }

    public AuthenticationSuccessHandler successHandler() {
        SimpleUrlAuthenticationSuccessHandler handler = new SimpleUrlAuthenticationSuccessHandler();
        handler.setDefaultTargetUrl("/");
        return handler;
    }

    //@Bean
    public InMemoryClientRegistrationRepository clientRegistrations() {
        ClientRegistration clientRegistration = ClientRegistration
                .withRegistrationId("my-client")
                .tokenUri("https://auth-server.com/oauth2/token")
                .clientId("my-client-id")
                .clientSecret("my-client-secret")
                .authorizationGrantType(org.springframework.security.oauth2.core.AuthorizationGrantType.CLIENT_CREDENTIALS)
                .build();

        return new InMemoryClientRegistrationRepository(clientRegistration);
    }

    @Bean
    public OAuth2AuthorizedClientService authorizedClientService(
            ClientRegistrationRepository clientRegistrationRepository) {
        return new InMemoryOAuth2AuthorizedClientService(clientRegistrationRepository);
    }

    @Bean
    public OAuth2AuthorizedClientManager authorizedClientManager(
            ClientRegistrationRepository clientRegistrationRepository,
            OAuth2AuthorizedClientService authorizedClientService) {
        var authorizedClientProvider =
                OAuth2AuthorizedClientProviderBuilder
                        .builder()
                        .clientCredentials()
                        .build();

        var clientManager = new AuthorizedClientServiceOAuth2AuthorizedClientManager(
                clientRegistrationRepository,
                authorizedClientService
        );

        clientManager.setAuthorizedClientProvider(authorizedClientProvider);

        return clientManager;
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        var jwtAuthenticationConverter = new JwtAuthenticationConverter();

        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(
                jwt -> {
                    List<String> userRolesAuthorities = jwt.getClaimAsStringList("authorities");

                    if(userRolesAuthorities == null) {
                        userRolesAuthorities = Collections.emptyList();
                    }
                    JwtGrantedAuthoritiesConverter scopesConverter = new JwtGrantedAuthoritiesConverter();
                    Collection<GrantedAuthority> scopeAuthorities = scopesConverter.convert(jwt);

                    scopeAuthorities.addAll(userRolesAuthorities
                            .stream()
                            .map(org.springframework.security.core.authority.SimpleGrantedAuthority::new)
                            .toList());

                    return scopeAuthorities;
                }
        );
        return jwtAuthenticationConverter;
    }

    @Bean
    public RSAKey rsaKey() throws Exception {
        return new RSAKeyGenerator(2048)
                .keyID("test-key-id")
                .generate();
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withJwkSetUri("http://localhost:8080/.well-known/jwks.json").build();
    }



}
