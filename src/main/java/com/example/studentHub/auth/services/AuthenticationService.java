package com.example.studentHub.auth.services;

import com.example.studentHub.auth.models.AuthenticationResponse;
import com.example.studentHub.auth.models.User;
import com.example.studentHub.auth.repositories.UserRepository;
import com.example.studentHub.filters.UserDetailsImplementation;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;


    public AuthenticationService(UserRepository repository, PasswordEncoder passwordEncoder, JwtService jwtService, UserDetailsImplementation userDetailsService, AuthenticationManager authenticationManager) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public Object register(User req) {
        // Check if the user already exists
        Optional<User> existingUser = repository.findByUsername(req.getUsername());

        if (existingUser.isPresent()) {
            return "User already exists";
        }

        // Create a new user if not already present
        User user = new User();
        user.setFirstName(req.getFirstName());
        user.setLastName(req.getLastName());
        user.setUsername(req.getUsername());
        user.setEmail(req.getEmail());
        user.setRole(req.getRole());
        user.setPassword(passwordEncoder.encode(req.getPassword()));

        // Save the new user
        user = repository.save(user);

        // Generate tokens for the new user
        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        // Return the tokens in the authentication response
        return new AuthenticationResponse(accessToken, refreshToken);
    }


    public AuthenticationResponse authenticate(User req) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword()));

        User user = repository.findByUsername(req.getUsername()).orElseThrow();
        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        return new AuthenticationResponse(accessToken, refreshToken);
    }

    public String refreshToken(String refreshToken) {
        String username = jwtService.extractUsername(refreshToken);
        User user = repository.findByUsername(username).orElseThrow();

        return jwtService.generateAccessToken(user);
    }

    public Optional<User> getUser(String token) {
        if (!token.isEmpty()) {
            String username = jwtService.extractUsername(token);
            return repository.findByUsername(username);
        } else {
            return Optional.empty();
        }
    }
}