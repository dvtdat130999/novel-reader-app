package com.novelreader.demo.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthenticationRequest {
    @NotBlank(message = "Email is required")
    @Email(message = "Email invalid")
    String email;

    @NotBlank(message = "Password is required")
    String password;
}
