package io.samwells.user_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.samwells.user_service.dto.request.UserAuthRequest;
import io.samwells.user_service.entity.User;
import io.samwells.user_service.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("integration")
@Transactional
@Rollback
public class SignupControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void signup_withInvalidRequest_shouldReturnBadRequest() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/signup")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void signup_withValidRequest_shouldReturnSuccess() throws Exception {
        String email = "test@test.com";
        String password = "funtesthehe";

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new UserAuthRequest(email, password)))
        ).andExpect(MockMvcResultMatchers.status().isOk());

        Assertions.assertNotNull(userRepository.findByEmail(email));
    }

    @Test
    public void signup_withExistingUser_shouldReturnConflict() throws Exception {
        String email = "test@test.com";
        String password = "funtesthehe";

        userRepository.save(new User(email, passwordEncoder.encode(password)));

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new UserAuthRequest(email, password)))
        ).andExpect(MockMvcResultMatchers.status().isConflict());
    }
}
