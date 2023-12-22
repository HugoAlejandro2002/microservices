package com.upb.authservice.service;

import com.upb.authservice.entity.User;
import com.upb.authservice.model.JwtAuthenticationResponse;
import com.upb.authservice.model.LoginRequest;
import com.upb.authservice.model.SignUpRequest;
import com.upb.authservice.model.UserResponse;

public interface AuthenticationService {
    public User signUp (SignUpRequest signUpRequest);
    public JwtAuthenticationResponse login(LoginRequest loginRequest);

    UserResponse validateToken(String token);

}
