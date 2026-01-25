package com.novelreader.demo.dto.request;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/** DTO for {@link com.novelreader.demo.entity.User} */
@Getter
@Setter
public class UserRegistrationRequest implements Serializable {
    @NotNull(message = "NULL_EXCEPTION")
    @NotEmpty(message = "EMPTY_EXCEPTION")
    @NotBlank(message = "BLANK_EXCEPTION")
    @Size(min = 3, message = "USERNAME_INVALID") // Key in error code
    String username;

    @NotNull(message = "NULL_EXCEPTION")
    @NotEmpty(message = "EMPTY_EXCEPTION")
    @NotBlank(message = "BLANK_EXCEPTION")
    @Email(message = "EMAIL_INVALID")
    String email;

    @NotNull(message = "NULL_EXCEPTION")
    @NotEmpty(message = "EMPTY_EXCEPTION")
    @NotBlank(message = "BLANK_EXCEPTION")
    @Size(min = 6, message = "PASSWORD_INVALID")
    String password;
}
