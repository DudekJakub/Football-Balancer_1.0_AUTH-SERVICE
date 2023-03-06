package com.dudek.authservice.service;

import com.dudek.authservice.mapper.UserMapper;
import com.dudek.authservice.model.SexStatus;
import com.dudek.authservice.model.dto.login.LoginDto;
import com.dudek.authservice.model.dto.login.LoginRequestDto;
import com.dudek.authservice.model.dto.user.UserDto;
import com.dudek.authservice.model.dto.user.UserRegisterRequestDto;
import com.dudek.authservice.model.entity.User;
import com.dudek.authservice.model.security.Role;
import com.dudek.authservice.model.security.UserDetailsImpl;
import com.dudek.authservice.repository.UserRepository;
import com.dudek.authservice.config.security.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public AuthService(final UserRepository userRepository, final UserMapper userMapper, final AuthenticationManager authenticationManager,
                       final JwtUtil jwtUtil, final PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    public LoginDto loginUser(final LoginRequestDto requestDto) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(requestDto.getUsername(), requestDto.getPassword());

        Authentication authentication = authenticationManager
                .authenticate(authenticationToken);

        if (!authentication.isAuthenticated()) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);

        UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();
        String generatedToken = jwtUtil.generateToken(user);

        return new LoginDto(generatedToken, user);
    }

    @Transactional
    public UserDto register(final UserRegisterRequestDto requestDto) {
        User authUser = User.builder()
                .username(requestDto.getUsername())
                .firstName(requestDto.getFirstName())
                .lastName(requestDto.getLastName())
                .sex(SexStatus.valueOf(requestDto.getSex()))
                .role(Role.USER)
                .password(requestDto.getPassword())
                .email(requestDto.getEmail())
                .build();

        if (userRepository.existsByUsernameIgnoreCase(authUser.getUsername())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists!");
        }

        authUser.setPassword(passwordEncoder.encode(authUser.getPassword()));

        return userMapper.userToDto(userRepository.save(authUser));
    }
}
