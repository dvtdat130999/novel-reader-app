package com.novelreader.demo.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/** DTO for {@link com.novelreader.demo.entity.User} */
@Getter
@Setter
public class UserRegistrationRequest implements Serializable {
    @NotNull @NotEmpty @NotBlank String username;

    @NotNull @Email @NotEmpty @NotBlank String email;

    @NotNull @NotEmpty @NotBlank String password;
}
