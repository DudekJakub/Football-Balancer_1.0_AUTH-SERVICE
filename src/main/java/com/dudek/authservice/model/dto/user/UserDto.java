package com.dudek.authservice.model.dto.user;

import com.dudek.authservice.model.SexStatus;
import com.dudek.authservice.model.security.Role;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class UserDto {

    private Long id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private SexStatus sex;
    private Role userRole;
}
