package com.dudek.authservice.controller;

import com.dudek.authservice.model.dto.user.UserDto;
import com.dudek.authservice.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/user")
@Tag(name = "User", description = "This API provides all basics operations about user (except auth operations).")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/info")
    public ResponseEntity<UserDto> userInfo(@RequestParam Long userId) {
        return new ResponseEntity<>(userService.getUserInfo(userId), HttpStatus.OK);
    }
}
