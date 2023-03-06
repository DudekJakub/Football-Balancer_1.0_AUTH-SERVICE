package com.dudek.authservice.model.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserDetailsMixin extends UserDetails {

    @JsonIgnore
    @Override
    default boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    default boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    default boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    default boolean isEnabled() {
        return true;
    }
}
