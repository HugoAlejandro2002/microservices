package com.upb.authservice.controller;

import com.upb.authservice.entity.ERole;
import com.upb.authservice.model.LoginRequest;
import com.upb.authservice.model.SignUpRequest;
import com.upb.authservice.model.UserResponse;
import com.upb.authservice.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@Log4j2
@RequestMapping("/api/auth")
public class AuthenticationController {


    private final AuthenticationService authenticationService;


    @PostMapping("/signup")
    public ResponseEntity<?> authenticateUser(@RequestBody SignUpRequest signUpRequest) {
        return ResponseEntity.ok(authenticationService.signUp(signUpRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<?> registerUser(@RequestBody LoginRequest loginRequest) {
        log.info("Login request for user: {}", loginRequest.getUsername());
        try {
            return ResponseEntity.ok(authenticationService.login(loginRequest));
        } catch (Exception e) {
            log.error("Login failed for user: {}", loginRequest.getUsername(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Login failed");
        }
    }

    @GetMapping("/validate")
    public ResponseEntity<?> validateToken(@RequestParam("token") String token) {
        try {
            UserResponse userData = authenticationService.validateToken(token);
            return ResponseEntity.ok(userData);
        } catch (Exception e) {
            log.error("Token validation failed", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Token validation failed");
        }
    }

    @GetMapping("/validateAdmin")
    public ResponseEntity<?> validateAdminToken(@RequestParam("token") String token) {
        try {
            UserResponse userData = authenticationService.validateToken(token);
            boolean isAdmin = userData.getRole().equals(ERole.ROLE_ADMIN); // Asumiendo que los roles están dentro de UserResponse
            if (isAdmin) {
                return ResponseEntity.ok(userData);
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
            }
        } catch (Exception e) {
            log.error("Admin token validation failed", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Token validation failed");
        }
    }
}
