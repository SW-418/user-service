package io.samwells.user_service.service;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import io.samwells.user_service.exception.UserAlreadyExistsException;
import io.samwells.user_service.repository.UserRepository;
import io.samwells.user_service.entity.User;

@Service
public class UserService implements UserDetailsManager {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByEmail(username);
        if (user == null) throw new UsernameNotFoundException("User not found");
        return user;
    }

    @Override
    public void createUser(UserDetails user) {
        try {
            userRepository.save(new User(user.getUsername(), passwordEncoder.encode(user.getPassword())));
        } catch (DataIntegrityViolationException e) {
            throw new UserAlreadyExistsException();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateUser(UserDetails user) {
        throw new UnsupportedOperationException("Unimplemented method 'updateUser'");
    }

    @Override
    public void deleteUser(String username) {
        throw new UnsupportedOperationException("Unimplemented method 'deleteUser'");
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {
        throw new UnsupportedOperationException("Unimplemented method 'changePassword'");
    }

    @Override
    public boolean userExists(String username) {
        return userRepository.findByEmail(username) != null;
    }
}
