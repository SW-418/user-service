package io.samwells.user_service.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserAuthRequest(@NotBlank @Email String email, @NotBlank @Size(min = 8) String password) {}
