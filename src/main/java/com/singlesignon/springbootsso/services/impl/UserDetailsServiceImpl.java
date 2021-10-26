package com.singlesignon.springbootsso.services.impl;

import com.singlesignon.springbootsso.dtos.UserDetails;
import com.singlesignon.springbootsso.exceptions.UnAuthorizedException;
import com.singlesignon.springbootsso.model.User;
import com.singlesignon.springbootsso.repository.UserRepository;
import com.singlesignon.springbootsso.services.UserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    private Authentication authentication;

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails getUserDetails() {

        authentication = SecurityContextHolder.getContext().getAuthentication();
        if ((authentication instanceof AnonymousAuthenticationToken))
            throw new UnAuthorizedException("Anonymous authorization not allowed");

        DefaultOidcUser oidcUser = (DefaultOidcUser) authentication.getPrincipal();

        String tokenValue = oidcUser.getIdToken().getTokenValue();

        String sub = oidcUser.getAttribute("sub");
        String givenName = oidcUser.getAttribute("given_name");
        String name = oidcUser.getAttribute("name");
        String familyName = oidcUser.getAttribute("family_name");
        String email = oidcUser.getAttribute("email");
        String userName = oidcUser.getAttribute("preferred_username");

        UserDetails userDetails = new UserDetails();
        userDetails.setUserName(userName);

        if (StringUtils.isBlank(userDetails.getEmail()))
            userDetails.setEmail(userName);
        else
            userDetails.setEmail(email);

        userDetails.setFamilyName(familyName);
        userDetails.setName(name);
        userDetails.setGivenName(givenName);
        userDetails.setSub(sub);
        userDetails.setIdToken(tokenValue);

        addUserIfNotExists(userDetails);

        return userDetails;
    }


    private void addUserIfNotExists(UserDetails userDetails) {

        // get the user by email ...
        User user = userRepository.findByEmail(userDetails.getEmail());

        if (user != null) {
            log.info(String.format("Email Id: '%s' exists. User will not be created...", user.getEmail()));
            return;
        }

        user = new User();
        user.setCreatedOn(LocalDateTime.now());
        user.setEmail(userDetails.getEmail());
        user.setName(userDetails.getName());

        userRepository.save(user);
        log.info(String.format("User with email id: '%s' created successfully", user.getEmail()));
    }
}
