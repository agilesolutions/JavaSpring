package com.agilesolutions.security;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Map;


@RestController
@AllArgsConstructor
public class FakeTokenController {

        private final RSAKey rsaKey;

        @PostMapping("/oauth2/token")
        public Map<String, Object> token() throws JOSEException {

            // Signer with private key
            JWSSigner signer = new RSASSASigner(rsaKey.toPrivateKey());

            // Claims inside JWT
            JWTClaimsSet claims = new JWTClaimsSet.Builder()
                    .subject("service-client")
                    .issuer("http://localhost:8080")
                    .audience("my-api")
                    .claim("roles", java.util.List.of("admin", "user"))
                    .claim("scope", "read write")
                    .expirationTime(new Date(System.currentTimeMillis() + 3600_000))
                    .issueTime(new Date())
                    .build();

            SignedJWT signedJWT = new SignedJWT(
                    new JWSHeader.Builder(JWSAlgorithm.RS256)
                            .keyID(rsaKey.getKeyID())
                            .build(),
                    claims
            );

            signedJWT.sign(signer);

            // Response in OAuth2 token form

            return Map.of(
                    "access_token", signedJWT.serialize(),
                    "token_type", "Bearer",
                    "expires_in", 3600
            );
        }
}
