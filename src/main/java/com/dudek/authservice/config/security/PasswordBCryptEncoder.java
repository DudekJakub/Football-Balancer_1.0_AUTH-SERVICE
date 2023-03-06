package com.dudek.authservice.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PasswordBCryptEncoder {

    @Bean
    public PasswordEncoder passwordEncryptor() {
        return new BCryptPasswordEncoder();
    }
}
