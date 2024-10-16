package com.albuquerque.gotreasury.controller;

import com.albuquerque.gotreasury.dto.LoginDTO;
import com.albuquerque.gotreasury.dto.TokenDTO;
import com.albuquerque.gotreasury.dto.UserDTO;
import com.albuquerque.gotreasury.model.User;
import com.albuquerque.gotreasury.service.AuthenticationService;
import com.albuquerque.gotreasury.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
@AllArgsConstructor
public class UserController {

        private final UserService userService;
        private final AuthenticationService authenticationService;

        @PostMapping("register")
        public ResponseEntity<User> registerUser(@RequestBody @Valid UserDTO user) {
            return ResponseEntity.ok(userService.registerUser(user));
        }

        @PostMapping("login")
        public ResponseEntity<TokenDTO> login(@RequestBody LoginDTO login) {
            TokenDTO tokenDTO = new TokenDTO(authenticationService.login(login.email(), login.password()));
            return ResponseEntity.ok(tokenDTO);
        }
}
