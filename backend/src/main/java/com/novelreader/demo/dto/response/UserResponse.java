package com.novelreader.demo.dto.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.io.Serializable;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/** DTO for {@link com.novelreader.demo.entity.User} */
@Getter
@Setter
@Builder
public class UserResponse implements Serializable {
    @NotNull @Positive Long id;

    @NotNull @NotEmpty @NotBlank String username;

    @NotNull @NotEmpty @NotBlank String email;
}
