package com.dudek.authservice.model.dto.login;

import com.dudek.authservice.model.security.UserDetailsImpl;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class LoginDto {

    private String token;
    private Long id;
    private String username;

    public LoginDto(String generatedToken, UserDetailsImpl userDetails) {
        this.token = generatedToken;
        this.id = userDetails.getUser().getId();
        this.username = userDetails.getUsername();
    }
}
