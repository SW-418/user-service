package io.samwells.user_service.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/signup")
public class SignUpController {
    public SignUpController() {}
    
    @PostMapping
    public ResponseEntity<String> signup() {
        return ResponseEntity.ok("Signup");
    }
}
