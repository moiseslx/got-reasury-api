package com.albuquerque.gotreasury.dto;

import com.albuquerque.gotreasury.model.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record UserDTO(
        @NotBlank(message = "Name cannot be null") String name,
        @Email(message = "Invalid email") String email,
        @Min(value = 6, message = "Password must be at least 6 characters long") String password) {

    public User toEntity() {
        return new User(null, name, email, password);
    }
}
