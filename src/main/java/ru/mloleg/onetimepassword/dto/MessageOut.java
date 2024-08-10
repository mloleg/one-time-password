package ru.mloleg.onetimepassword.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.util.UUID;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record MessageOut(
        UUID id,
        MessageStatus status,
        String errorMessage) {

}
