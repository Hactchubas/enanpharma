package com.ecommerce.enanpharma.service;

import com.ecommerce.enanpharma.dto.AuthResponse;
import com.ecommerce.enanpharma.dto.LoginRequest;

public interface AuthService {
    AuthResponse login(LoginRequest loginRequest);
}
