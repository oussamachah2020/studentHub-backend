package com.example.studentHub.auth.controller;

import com.example.studentHub.auth.models.AuthenticationResponse;
import com.example.studentHub.auth.models.User;
import com.example.studentHub.auth.services.AuthenticationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User req) {
        Object response = authenticationService.register(req);

        if (response instanceof AuthenticationResponse) {
            // Return a 200 OK response with the AuthenticationResponse
            return ResponseEntity.ok((AuthenticationResponse) response);
        } else if (response instanceof String && response.equals("User already exists")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        } else {
            // Fallback for any unexpected cases
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Unexpected error occurred");
        }
    }


    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody User req) {
        AuthenticationResponse tokenObject = authenticationService.authenticate(req);

        AuthenticationResponse authResponse = new AuthenticationResponse(tokenObject.getAccessToken(), tokenObject.getRefreshToken());

        return ResponseEntity.ok(authResponse);
    }

    @PostMapping(path = "/refresh", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> refreshToken(@RequestParam(name = "token") String refreshToken) {
        // Call the service to refresh the token
        String token = authenticationService.refreshToken(refreshToken);

        // Create a response map
        Map<String, String> response = new HashMap<>();
        response.put("accessToken", token);

        // Return the response as JSON
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user-info") // Add your endpoint path
    public ResponseEntity<?> getUserInfo(@RequestHeader("Authorization") String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            // Now you can use the token to get the user
            Optional<User> user = authenticationService.getUser(token);

            if (user.isPresent()) {
                return ResponseEntity.ok(user.get()); // Return the user object if found
            } else {
                return ResponseEntity.status(404).body("User not found"); // Return 404 if the user is not found
            }
        } else {
            return ResponseEntity.badRequest().body("Invalid Authorization header"); // Return bad request if header is invalid
        }
    }

}