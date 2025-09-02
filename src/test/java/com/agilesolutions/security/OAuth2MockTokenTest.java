package com.agilesolutions.security;

import com.agilesolutions.jpa.controller.JpaShareController;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.gen.RSAKeyGenerator;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static com.github.tomakehurst.wiremock.client.WireMock.okJson;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;
import java.util.List;

//import static com.github.tomakehurst.wiremock.client.WireMock.*;


@WebMvcTest(JpaShareController.class)
@ContextConfiguration(classes = {FakeTokenController.class, JwksController.class})
@TestPropertySource(properties = { "spring.config.location=classpath:application.yaml" })
public class OAuth2MockTokenTest {


    private static WireMockServer wireMockServer;
    private static RSAKey rsaKey;

    @Autowired
    private MockMvc mockMvc;

    @BeforeAll
    static void setup() {
        // Start WireMock on port 8089
        wireMockServer = new WireMockServer(8089);
        wireMockServer.start();

        // Generate RSA test keypair
        rsaKey = new RSAKeyGenerator(2048).keyID("test-key-id").generate();

        // Serve JWKS with public key
        JWKSet jwkSet = new JWKSet(rsaKey.toPublicJWK());
        wireMockServer.stubFor(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get(urlEqualTo("/.well-known/jwks.json"))
                .willReturn(okJson(jwkSet.toString())));
    }

    @AfterAll
    static void teardown() {
        wireMockServer.stop();
    }

    @Test
    void adminCanAccessAdminEndpoint() throws Exception {
        String jwt = generateJwt(List.of("admin"));

        mockMvc.perform(get("/api/admin")
                        .header("Authorization", "Bearer " + jwt))
                .andExpect(status().isOk());
    }

    @Test
    void userWithoutAdminRoleIsForbidden() throws Exception {
        String jwt = generateJwt(List.of("user"));

        mockMvc.perform(get("/api/admin")
                        .header("Authorization", "Bearer " + jwt))
                .andExpect(status().isForbidden());
    }

    private String generateJwt(List<String> roles) throws Exception {
        JWSSigner signer = new RSASSASigner(rsaKey.toPrivateKey());

        JWTClaimsSet claims = new JWTClaimsSet.Builder()
                .subject("test-user")
                .issuer("http://localhost:8089")
                .audience("my-api")
                .claim("roles", roles)
                .expirationTime(new Date(System.currentTimeMillis() + 3600_000))
                .issueTime(new Date())
                .build();

        SignedJWT signedJWT = new SignedJWT(
                new JWSHeader.Builder(JWSAlgorithm.RS256).keyID(rsaKey.getKeyID()).build(),
                claims
        );

        signedJWT.sign(signer);
        return signedJWT.serialize();
    }

    
}
