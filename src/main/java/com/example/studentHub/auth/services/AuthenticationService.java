package com.example.studentHub.auth.services;

import com.example.studentHub.auth.models.AuthenticationResponse;
import com.example.studentHub.auth.models.User;
import com.example.studentHub.auth.models.dto.AuthDto;
import com.example.studentHub.auth.models.dto.ProfileDto;
import com.example.studentHub.auth.repositories.UserRepository;
import com.example.studentHub.filters.UserDetailsImplementation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
        Optional<User> existingUser = repository.findByEmail(req.getEmail());
        String message = "";
        if (existingUser.isPresent()) {
            return "User already exists";
        }

        // Create a new user if not already present
        User user = new User();

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


    public ResponseEntity<?> authenticate(AuthDto req) {
        // Determine if the input is an email or a username
        User user;
        String message;

        if (req.getUsername().contains("@")) {
            // If the input contains "@", assume it's an email
            user = repository.findByEmail(req.getUsername())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User with this email does not exist."));

            if(user == null) {
                return ResponseEntity.status(404).body("User with this e-mail does not exist.");
            }

        } else {
            // Otherwise, assume it's a username
            user = repository.findByUsername(req.getUsername())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User with this username does not exist."));

            if(user == null) {
                return ResponseEntity.status(404).body("User with this username does not exist.");
            }
        }

        // Check if the password is correct
        if (!passwordEncoder.matches(req.getPassword(), user.getPassword())) {
            return ResponseEntity.status(400).body("Invalid password");
        }

        // Authenticate the user
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), req.getPassword())
        );

        // Generate access and refresh tokens
        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        AuthenticationResponse tokenObject = new AuthenticationResponse(accessToken, refreshToken);
        AuthenticationResponse authResponse = new AuthenticationResponse(tokenObject.getAccessToken(), tokenObject.getRefreshToken());

        return ResponseEntity.ok(authResponse);
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

    public String updateProfile(ProfileDto req) {
        User user = repository.findByEmail(req.getEmail()).orElseThrow();

        user.setUsername(req.getUsername());
        user.setFirstName(req.getFirstName());
        user.setLastName(req.getLastName());
        user.setPhone(req.getPhone());
        user.setPhotoUrl(req.getPhotoUrl());

        repository.save(user);

        return "Profile updated successfully";

    }
}