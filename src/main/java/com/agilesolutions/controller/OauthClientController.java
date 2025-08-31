package com.agilesolutions.controller;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Log4j2
@AllArgsConstructor
@RestController
@RequestMapping("/oauthClient")
public class OauthClientController {

    //private final RestClient restClient;

    /*
    @GetMapping("/shares")
    public ResponseEntity<List<ShareDTO>> messages() {
        ShareDTO[] shares = this.restClient.get()
                .uri("http://localhost:8080//api/jpa/shares")
                .attributes(clientRegistrationId("oauthclient"))
                .retrieve()
                .body(ShareDTO[].class);

        return ResponseEntity.ok(Arrays.asList(shares));
    }
     */

    @GetMapping("/")
    public ModelAndView getUserInfoPage(@AuthenticationPrincipal OAuth2User user) {
        ModelAndView modelAndView = new ModelAndView("userinfo");
        modelAndView.addObject("user", Map.of(
                "email", user.getAttribute("email"),
                "id", user.getAttribute("sub")
        ));

        return modelAndView;
    }

    @GetMapping("/login")
    public String getSsoPage(Model model) {
        return "login";
    }







}
