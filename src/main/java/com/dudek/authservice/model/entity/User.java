package com.dudek.authservice.model.entity;

import com.dudek.authservice.model.SexStatus;
import com.dudek.authservice.model.security.Role;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String firstName;

    @NotBlank
    private String username;

    @NotBlank
    private String lastName;

    @NotBlank
    private String password;

    @Enumerated(value = EnumType.STRING)
    @NotNull
    private SexStatus sex;

    @Enumerated(value = EnumType.STRING)
    @NotNull
    private Role role;

    @Email
    private String email;
}
