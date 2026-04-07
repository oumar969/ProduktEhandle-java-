package com.ehandle.controller;

import com.ehandle.dto.JwtResponse;
import com.ehandle.dto.LoginRequest;
import com.ehandle.dto.RegisterRequest;
import com.ehandle.dto.UserDTO;
import com.ehandle.model.User;
import com.ehandle.service.AuthService;
import com.ehandle.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "User authentication endpoints")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    @Operation(summary = "Register a new user", description = "Create a new user account")
    public ResponseEntity<UserDTO> register(@Valid @RequestBody RegisterRequest request) {
        log.info("Registering new user: {}", request.getUsername());
        User user = authService.registerUser(request);
        return new ResponseEntity<>(userService.toDTO(user), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    @Operation(summary = "Login user", description = "Authenticate user and return JWT token")
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody LoginRequest request) {
        log.info("User login attempt: {}", request.getUsername());
        JwtResponse response = authService.loginUser(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/me")
    @Operation(summary = "Get current user", description = "Get authenticated user information")
    public ResponseEntity<UserDTO> getCurrentUser(Authentication authentication) {
        User user = userService.getUserByUsername(authentication.getName());
        return ResponseEntity.ok(userService.toDTO(user));
    }
}
