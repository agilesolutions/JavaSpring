package com.agilesolutions.security;

import com.agilesolutions.config.OauthConfig;
import com.agilesolutions.jpa.controller.JpaShareController;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
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
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = JpaShareController.class)
@SpringJUnitConfig(classes = {FakeTokenController.class, JwksController.class, OauthConfig.class, JpaShareController.class}, initializers = {ConfigDataApplicationContextInitializer.class})
@TestPropertySource(properties = { "spring.config.location=classpath:application.yaml" })
@AutoConfigureMockMvc
public class OAuth2MockTokenTest {

    private static WireMockServer wireMockServer;

    private static RSAKey rsaKey;

    @Autowired
    private MockMvc mockMvc;

    /**
     * This setup mocks one API  /.well-known/openid-configuration, a JSON file that an OpenID Connect provider serves over HTTPS at a well-known URL,
     * providing all the configuration details a client needs to integrate with it.
     * Allowing client applications to automatically discover auth endpoints, token endpoints, supported features, public keys, scopes, claims, etc.
     * @throws Exception
     */
    @BeforeAll
    static void setup() throws Exception {
        // Start WireMock on port 8089
        wireMockServer = new WireMockServer(8787);
        wireMockServer.start();

        // Generate RSA test keypair
        rsaKey = new RSAKeyGenerator(2048).keyID("test-key-id").generate();

        // Serve JWKS with public key
        JWKSet jwkSet = new JWKSet(rsaKey.toPublicJWK());
        wireMockServer.stubFor(WireMock.get(WireMock.urlEqualTo("/.well-known/jwks.json"))
                .willReturn(WireMock.okJson(jwkSet.toString())));
        wireMockServer.stubFor(WireMock.get(WireMock.urlEqualTo("/realms/my-realm/.well-known/openid-configuration"))
                .willReturn(WireMock.aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
            {
                "issuer": "http://localhost:8787/realms/my-realm",
                "authorization_endpoint": "http://localhost:8787/realms/my-realm/oauth/authorize",
                "token_endpoint": "http://localhost:8787/realms/my-realm/oauth/token",
                "userinfo_endpoint": "http://localhost:8787/realms/my-realm/userinfo",
                "jwks_uri": "http://localhost:8787/realms/my-realm/.well-known/jwks.json",
                "response_types_supported": [
                    "code",
                    "token",
                    "id_token",
                    "code token",
                    "code id_token",
                    "token id_token",
                    "code token id_token",
                    "none"
                ],
                "subject_types_supported": [
                    "public"
                ],
                "id_token_signing_alg_values_supported": [
                    "RS256"
                ],
                "scopes_supported": [
                    "openid",
                    "email",
                    "profile"
                ]
            }
        """)));

    }

    @AfterAll
    static void teardown() {
        wireMockServer.stop();
    }


    @Test
    void givenAdminRole_whenAccessingEndPoint_PermissionAllowed() throws Exception {
        String jwt = generateJwt(List.of("admin"));

        mockMvc.perform(get("/api/jpa/shares/healthCheck")
                        .header("Authorization", "Bearer " + jwt))
                .andExpect(status().isOk());
    }

    @Test
    void givenUserRole_whenAccessingEndPoint_PermissionAllowed() throws Exception {
        String jwt = generateJwt(List.of("user"));

        mockMvc.perform(get("/api/jpa/shares/healthCheck")
                        .header("Authorization", "Bearer " + jwt))
                .andExpect(status().isOk());
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
