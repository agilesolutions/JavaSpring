package com.agilesolutions.security;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class JwksController {

    private final RSAKey rsaKey;

    @GetMapping("/.well-known/jwks.json")
    public String keys() {
        return new JWKSet(rsaKey.toPublicJWK()).toString();
    }
}