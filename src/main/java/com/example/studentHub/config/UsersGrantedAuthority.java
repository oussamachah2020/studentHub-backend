package com.example.studentHub.config;

import org.springframework.security.core.GrantedAuthority;

public class UsersGrantedAuthority implements GrantedAuthority {
    private String authority;

    public UsersGrantedAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return this.authority;
    }
}