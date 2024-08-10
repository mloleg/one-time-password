package ru.mloleg.onetimepassword.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.hibernate.validator.constraints.Range;

import java.util.UUID;

@Builder
public record GenerateAndSendRequest(
        @NotNull(message = "UUID не может быть пустым")
        UUID processId,
        @NotEmpty(message = "Идентификатор чата не может быть пустым")
        String telegramChatId,
        @NotEmpty(message = "Сообщение не может быть пустым")
        String message,
        @NotNull(message = "Размер пароля не может быть пустым")
        @Range(min = 4, max = 8, message = "Размер пароля должен быть не меньше 4 и не больше 8 символов")
        Integer length,
        @NotNull(message = "Время жизни не может быть пустым")
        @Range(min = 30, message = "Время жизни должно быть не меньше 30 секунд")
        Integer ttl,
        @NotNull(message = "Количество повторных отправок не может быть пустым")
        @Range(min = 1, max = 3, message = "Количестов повторных отправок должно быть не меньше 1 и не больше 3")
        Integer resendAttempts,
        @NotNull(message = "Интервал между запросами не может быть пустым")
        @Range(min = 30, message = "Интервал между запросами не должен быть меньше 30 секунд")
        Integer resendTimeout) {

}
