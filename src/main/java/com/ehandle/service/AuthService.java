package com.ehandle.service;

import com.ehandle.dto.JwtResponse;
import com.ehandle.dto.LoginRequest;
import com.ehandle.dto.RegisterRequest;
import com.ehandle.model.User;
import com.ehandle.security.JwtTokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AuthService {

        @Autowired
        private AuthenticationManager authenticationManager;

        @Autowired
        private JwtTokenProvider jwtTokenProvider;

        @Autowired
        private UserService userService;

        public User registerUser(RegisterRequest registerRequest) {
                User user = User.builder()
                                .username(registerRequest.getUsername())
                                .email(registerRequest.getEmail())
                                .fullName(registerRequest.getFullName())
                                .build();

                return userService.createUser(user, registerRequest.getPassword());
        }

        public JwtResponse loginUser(LoginRequest loginRequest) {
                Authentication authentication = authenticationManager.authenticate(
                                new UsernamePasswordAuthenticationToken(
                                                loginRequest.getUsername(),
                                                loginRequest.getPassword()));

                String jwt = jwtTokenProvider.generateToken(authentication);
                User user = userService.getUserByUsername(loginRequest.getUsername());

                return JwtResponse.builder()
                                .token(jwt)
                                .id(user.getId())
                                .username(user.getUsername())
                                .email(user.getEmail())
                                .role(user.getRole().name())
                                .build();
        }
}
