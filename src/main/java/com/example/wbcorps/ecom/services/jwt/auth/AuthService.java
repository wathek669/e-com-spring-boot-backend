package com.example.wbcorps.ecom.services.jwt.auth;

import com.example.wbcorps.ecom.dto.SignupRequest;
import com.example.wbcorps.ecom.dto.UserDto;

public interface AuthService {
    UserDto createUser(SignupRequest signupRequest);

    Boolean hasUserWithEmail(String email);
}
