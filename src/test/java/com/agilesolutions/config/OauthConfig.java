package com.agilesolutions.config;

import com.agilesolutions.jpa.service.JpaShareService;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.gen.RSAKeyGenerator;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OauthConfig {


    @Bean
    public RSAKey rsaKey() throws Exception {
        return new RSAKeyGenerator(2048)
                .keyID("test-key-id")
                .generate();
    }

    @Bean
    public JpaShareService shareService() {
        return Mockito.mock(JpaShareService.class);
    }

}
