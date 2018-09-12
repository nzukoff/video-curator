package com.zukoff.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class JwtToken implements Authentication {

    private final String username;
    private final Collection<? extends GrantedAuthority> roles;
    private final Integer userId;
    private boolean authenticated;

    public JwtToken(Integer userId, String username, Collection<? extends GrantedAuthority> authorities) {
        this.userId = userId;
        this.username = username;
        this.roles = authorities;
        this.authenticated = true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        System.out.println("JWTTOKEN GET AUTHORITIES");
        return roles;
    }

    @Override
    public Object getCredentials() {
        System.out.println("JWTTOKEN GET CREDENTIALS");
        return null;
    }

    @Override
    public Object getDetails() {
        System.out.println("JWTTOKEN GET DETAILS");
        return null;
    }

    @Override
    public Object getPrincipal() {
        System.out.println("JWTTOKEN GET PRINCIPAL");
        return true;
    }

    @Override
    public boolean isAuthenticated() {
        System.out.println("JWTTOKEN IS AUTHENTICATED");
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        System.out.println("JWTTOKEN SET AUTHENTICATED");
        this.authenticated = isAuthenticated;
    }

    @Override
    public String getName() {
        System.out.println("JWTTOKEN GET NAME");
        return username;
    }
    public Integer getUserId() {
        System.out.println("JWTTOKEN GET USER ID");
        return userId;
    }
}