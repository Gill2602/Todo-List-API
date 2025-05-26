package com.gll.todoapi.services;

import com.gll.todoapi.entities.UserEntity;
import com.gll.todoapi.exceptions.AlreadyUsedException;
import com.gll.todoapi.repositories.UserRepository;
import com.gll.todoapi.requests.LoginRequest;
import com.gll.todoapi.requests.RegisterRequest;
import com.gll.todoapi.responses.AuthResponse;
import com.gll.todoapi.security.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final AuthService authService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new AlreadyUsedException("email already with an existing account");
        }

        UserEntity user = UserEntity.builder()
                .name(request.name())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .build();

        userRepository.save(user);

        return authService.authenticate(new LoginRequest(request.email(), request.password()));
    }
}
