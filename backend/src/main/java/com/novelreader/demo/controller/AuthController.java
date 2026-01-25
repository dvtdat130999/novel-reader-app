package com.novelreader.demo.controller;

import com.novelreader.demo.dto.request.UserRegistrationRequest;
import com.novelreader.demo.dto.response.ApiResponse;
import com.novelreader.demo.dto.response.UserResponse;
import com.novelreader.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    // API: POST /api/v1/auth/register
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserResponse>> register(
            @RequestBody UserRegistrationRequest request) {
        UserResponse data = userService.registerUser(request);

        ApiResponse<UserResponse> response =
            ApiResponse.<UserResponse>builder()
                .code(201)
                .message("User registered successfully")
                .result(data)
                .build();

        return ResponseEntity.status(201).body(response);
    }
}
