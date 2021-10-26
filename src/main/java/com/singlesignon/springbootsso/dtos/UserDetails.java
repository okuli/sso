package com.singlesignon.springbootsso.dtos;

import lombok.Data;

@Data
public class UserDetails {

    private String userName;
    private String sub;
    private String iss;
    private String givenName;
    private String name;
    private String familyName;
    private String email;
    private String idToken;
}
