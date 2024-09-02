package ru.mloleg.onetimepassword.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.UUID;

@Builder
public record CheckRequest(
        @NotNull(message = "UUID не может быть пустым")
        UUID processId,
        @NotNull(message = "Одноразовый код не может быть пустым")
        Integer otp) {

}
