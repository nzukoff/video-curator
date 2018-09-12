package com.zukoff.dtos;

public class AuthDto {
    private String username;
    private String password;

    public String getUsername() {
        System.out.println("AUTH DTO GET USERNAME");
        return username;
    }
    public void setUsername(String username) {
        System.out.println("AUTH DTO SET USERNAME");
        this.username = username;
    }

    public String getPassword() {
        System.out.println("AUTH DTO GET PASSWORD");
        return password;
    }
    public void setPassword(String password) {
        System.out.println("AUTH DTO SET PASSWORD");
        this.password = password;
    }
}
