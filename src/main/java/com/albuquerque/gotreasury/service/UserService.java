package com.albuquerque.gotreasury.service;

import com.albuquerque.gotreasury.dto.UserDTO;
import com.albuquerque.gotreasury.model.User;
import com.albuquerque.gotreasury.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User registerUser(UserDTO user) {
        User value = user.toEntity();
        value.setPassword(passwordEncoder.encode(value.getPassword()));

        if (userRepository.findByEmail(user.email()).isPresent()) {
            throw new RuntimeException("Email already in use");
        }

        return userRepository.save(value);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
