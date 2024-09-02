package ru.mloleg.onetimepassword.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record MessageRequest(
        UUID id,
        String telegramChatId,
        String message) {

}
