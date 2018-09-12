package com.zukoff.dtos;

import java.util.List;

public class UserDto {
    private String username;
    private List<String> roles;

    public UserDto(String username, List<String> roles) {
        this.username = username;
        this.roles = roles;
    }

    public String getUsername() {
        System.out.println("USER DTO GET USERNAME");
        return username;
    }

    public void setUsername(String username) {
        System.out.println("USER DTO SET USERNAME");
        this.username = username;
    }

    public List<String> getRoles() {
        System.out.println("USER DTO GET ROLES");
        return roles;
    }

    public void setRoles(List<String> roles) {
        System.out.println("USER DTO SET ROLES");
        this.roles = roles;
    }
}
