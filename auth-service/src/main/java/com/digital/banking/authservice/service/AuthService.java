package com.digital.banking.authservice.service;

import com.digital.banking.authservice.dto.AuthRequest;
import com.digital.banking.authservice.dto.AuthResponse;
import com.digital.banking.authservice.dto.RegisterRequest;
import com.digital.banking.authservice.model.Role;
import com.digital.banking.authservice.entity.User;
import com.digital.banking.authservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;


    public AuthResponse register(RegisterRequest request) {
        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        userRepository.save(user);

        String token = jwtService.generateToken(user.getEmail());
        return AuthResponse.builder().token(token).build();
    }

    public AuthResponse authenticate(AuthRequest request) {
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtService.generateToken(user.getEmail());
        return AuthResponse.builder().token(token).build();
    }
}
