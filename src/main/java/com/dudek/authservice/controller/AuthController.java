package com.dudek.authservice.controller;

import com.dudek.authservice.model.dto.login.LoginDto;
import com.dudek.authservice.model.dto.login.LoginRequestDto;
import com.dudek.authservice.model.dto.user.UserDto;
import com.dudek.authservice.model.dto.user.UserRegisterRequestDto;
import com.dudek.authservice.service.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@Tag(name = "Auth", description = "This API provides all basics auth operations for user.")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(final AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginDto> loginUser(@Valid @RequestBody LoginRequestDto requestDto) {
        return ResponseEntity.ok(authService.loginUser(requestDto));
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UserDto> registerUser(@Valid @RequestBody UserRegisterRequestDto requestDto) {
        return new ResponseEntity<>(authService.register(requestDto), HttpStatus.CREATED);
    }
}
