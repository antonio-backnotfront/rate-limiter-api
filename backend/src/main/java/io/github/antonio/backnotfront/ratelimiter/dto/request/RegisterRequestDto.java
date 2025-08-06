package io.github.antonio.backnotfront.ratelimiter.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegisterRequestDto(
        @NotBlank
        @Pattern(
                regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$",
                message = "invalid email.")
        String email,
        @NotBlank
//        @Size(min = 8, message = "password should be of a minimum length 8 and have at least " +
//                "one lowercase character, one uppercase character, and one digit")
        @Pattern(
                regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]{8,24}$",
                message = "password length should be between 8 and 24 and have at least " +
                        "one lowercase character, one uppercase character, and one digit")
        String password
) {
}
