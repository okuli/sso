package com.singlesignon.springbootsso.controllers;

import com.singlesignon.springbootsso.dtos.UserDetails;
import com.singlesignon.springbootsso.services.UserDetailsService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;

@Controller
public class WelcomeController {

    // inject via application.properties
    @Value("${welcome.message}")
    private String message;

    private final UserDetailsService userDetailsService;

    public WelcomeController(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @GetMapping("/")
    public String main(Model model, @AuthenticationPrincipal OidcUser oidcUser) throws MalformedURLException {

        String preferredUserName = "";
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof OidcUser) {
            OidcUser principal = ((OidcUser) authentication.getPrincipal());
            preferredUserName = principal.getFullName();
            // ...
        } else if (authentication.getPrincipal() instanceof DefaultOAuth2User) {
            DefaultOAuth2User principal = ((DefaultOAuth2User) authentication.getPrincipal());
            preferredUserName = principal.getAttribute("preferred_username");
        }

        model.addAttribute("message", message);
        model.addAttribute("authUser", preferredUserName);

        UserDetails userDetails = userDetailsService.getUserDetails();

        model.addAttribute("userName", userDetails.getUserName());
        model.addAttribute("sub", userDetails.getSub());
        model.addAttribute("givenName", userDetails.getGivenName());
        model.addAttribute("name", userDetails.getName());
        model.addAttribute("familyName", userDetails.getFamilyName());
        model.addAttribute("email", userDetails.getEmail());
        model.addAttribute("idToken", userDetails.getIdToken());


        return "welcome"; //view
    }

    // /hello?name=kotlin
    @GetMapping("/hello")
    public String mainWithParam(
            @RequestParam(name = "name", required = false, defaultValue = "")
                    String name, Model model) {

        model.addAttribute("message", name);

        return "welcome"; //view
    }
}
