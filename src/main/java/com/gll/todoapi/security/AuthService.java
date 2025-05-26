package com.gll.todoapi.security;

import com.gll.todoapi.requests.LoginRequest;
import com.gll.todoapi.responses.AuthResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authManager;
    private final JWTService jwtService;

    public AuthResponse authenticate(LoginRequest request) {
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );

        String jwtToken;
        if (authentication.isAuthenticated()) {
            jwtToken = jwtService.generateToken(request.email());
        }
        else {
            jwtToken = "Authentication failed";
        }

        return new AuthResponse(jwtToken);
    }
}
