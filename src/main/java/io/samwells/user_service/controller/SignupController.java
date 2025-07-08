package io.samwells.user_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.provisioning.UserDetailsManager;

import io.samwells.user_service.dto.request.UserAuthRequest;
import io.samwells.user_service.entity.User;

@RestController
@RequestMapping("/api/v1/signup")
public class SignupController {
    private final UserDetailsManager userDetailsManager;
    
    public SignupController(UserDetailsManager userDetailsManager) {
        this.userDetailsManager = userDetailsManager;
    }
    
    @PostMapping
    public ResponseEntity<String> signup(@Validated @RequestBody UserAuthRequest authRequest) {
        userDetailsManager.createUser(new User(authRequest.email(), authRequest.password()));
        return ResponseEntity.ok("User created successfully");
    }
}
