package com.example.commercepjt.common.utils.auth;

import com.example.commercepjt.domain.User;
import com.example.commercepjt.domain.UserBuyer;
import java.util.Collection;
import java.util.Collections;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class MyUserDetails implements UserDetails {

    private User user;

    @Getter
    private Long userId;

    public MyUserDetails(User user) {
        this.user = user;
        this.userId = user.getId();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String role = "ROLE_" + (user instanceof UserBuyer ? "BUYER" : "SELLER");
        return Collections.singleton(new SimpleGrantedAuthority(role));
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}