package io.samwells.user_service.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/login")
public class LoginController {
    public LoginController() {}
    
    @PostMapping
    public ResponseEntity<String> login() {
        return ResponseEntity.ok("Login");
    }
}
