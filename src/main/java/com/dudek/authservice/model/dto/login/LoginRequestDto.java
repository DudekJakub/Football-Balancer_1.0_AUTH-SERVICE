package com.dudek.authservice.model.dto.login;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class LoginRequestDto {

    @NotEmpty(message = "Username is required!")
    @Size(min = 4, message = "Too short for username!")
    private String username;

    @NotEmpty(message = "Password is required!")
    @Size(min = 6, message = "Password should be at least 6 characters!")
    private String password;
}
