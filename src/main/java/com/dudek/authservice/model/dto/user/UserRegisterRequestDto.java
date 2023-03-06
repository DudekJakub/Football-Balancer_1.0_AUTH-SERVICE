package com.dudek.authservice.model.dto.user;

import com.dudek.authservice.model.SexStatus;
import lombok.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class UserRegisterRequestDto {

    @NotBlank
    @Size(min = 4, max = 20)
    @Pattern(regexp = "^([a-zA-Z\\d]+)$")
    private String username;

    @NotBlank
    @Size(min = 8)
    @Pattern(regexp = "(?=.*\\d.*\\d)(?=.*\\W).*")
    private String password;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotNull
    private String sex;
}
