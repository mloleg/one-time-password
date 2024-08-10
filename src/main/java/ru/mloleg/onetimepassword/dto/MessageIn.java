package ru.mloleg.onetimepassword.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record MessageIn(
        UUID id,
        String telegramChatId,
        String message) {

}
