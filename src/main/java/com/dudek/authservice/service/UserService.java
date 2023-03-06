package com.dudek.authservice.service;

import com.dudek.authservice.mapper.UserMapper;
import com.dudek.authservice.model.dto.user.UserDto;
import com.dudek.authservice.model.entity.User;
import com.dudek.authservice.model.security.UserDetailsImpl;
import com.dudek.authservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserService(final UserRepository userRepository, final UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsernameIgnoreCase(username);

        return user.map(UserDetailsImpl::new)
                .orElseThrow(() -> new UsernameNotFoundException("User " + username + " not found!"));
    }

    public UserDto getUserInfo(final Long userId) {
        return userMapper.userToDto(userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.")));
    }
}
