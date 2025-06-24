
package io.samwells.user_service.service;

import io.samwells.user_service.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import io.samwells.user_service.repository.UserRepository;

public class UserServiceTest {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    private UserService userService;

    @BeforeEach
    public void setUp() {
        this.userRepository = Mockito.mock(UserRepository.class);
        this.passwordEncoder = Mockito.mock(PasswordEncoder.class);
        userService = new UserService(userRepository, passwordEncoder);
    }

    @Test
    public void loadUserByUsername_whenUserDoesNotExist_thenThrowUsernameNotFoundException() {
        String email = "test@gmail.com";
        Mockito.when(userRepository.findByEmail(email)).thenReturn(null);

        Assertions.assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername(email));
    }

    @Test
    public void loadUserByUsername_whenUserExists_thenReturnUser() {
        String email = "test@gmail.com";
        String password = "testpw";
        User expectedUser = new User(email, password);

        Mockito.when(userRepository.findByEmail(email)).thenReturn(expectedUser);

        UserDetails actualUser = Assertions.assertDoesNotThrow(() -> userService.loadUserByUsername(email));
        Assertions.assertEquals(email, actualUser.getUsername());
        Assertions.assertEquals(password, actualUser.getPassword());
    }
}
