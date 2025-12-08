package com.exam.stud.security;

import com.exam.stud.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class CustomUserDetails implements UserDetails {

    private final User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    // 1. Authorities: Maps your "UserRole" (ENUM) to Spring Security "Authorities"
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Converts "STUDENT" -> "ROLE_STUDENT"
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail(); // We use Email as the username for login
    }
    
    // Helper method to get the ID easily later
    public String getUserId() {
        return user.getUserId();
    }

    // 2. Standard Spring Security flags (We set them all to true for now)
    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }
    
    public User getUser() {
        return this.user;
    }
}