package io.samwells.user_service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import io.samwells.user_service.dto.request.UserAuthRequest;
import io.samwells.user_service.dto.response.UserLoginResponse;
import io.samwells.user_service.entity.User;
import io.samwells.user_service.exception.InvalidCredentialsException;
import io.samwells.user_service.utils.JWTUtils;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

@RestController
@RequestMapping("/api/v1/login")
public class LoginController {
    // Could move this to the service level
    private final AuthenticationManager authenticationManager;
    private final JWTUtils jwtUtils;
    
    public LoginController(AuthenticationManager authenticationManager, JWTUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }
    
    @PostMapping
    public ResponseEntity<UserLoginResponse> login(@Validated @RequestBody UserAuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.email(), authRequest.password()));
        if (!authentication.isAuthenticated()) throw new InvalidCredentialsException();

        var user = (User) authentication.getPrincipal();
        
        return ResponseEntity.ok(new UserLoginResponse(jwtUtils.generateToken(user)));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleMissingBody(HttpMessageNotReadableException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Request body is missing or malformed");
    }
}
