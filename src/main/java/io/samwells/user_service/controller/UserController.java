package io.samwells.user_service.controller;

import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import io.samwells.user_service.entity.User;
import io.samwells.user_service.dto.response.UserResponse;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    public UserController() {}

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable Long id) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var user = (User) authentication.getPrincipal();

        if (user.getId() != id) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        
        return ResponseEntity.ok(new UserResponse(user.getId(), user.getUsername(), user.getCreatedAt().toString()));
    }    
}
