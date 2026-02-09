package com.novelreader.demo.service;

import com.novelreader.demo.dto.request.AuthenticationRequest;
import com.novelreader.demo.dto.request.UserRegistrationRequest;
import com.novelreader.demo.dto.response.AuthenticationResponse;
import com.novelreader.demo.dto.response.UserResponse;

public interface UserService {
    UserResponse registerUser(UserRegistrationRequest request);
    AuthenticationResponse authenticate(AuthenticationRequest request);
}
