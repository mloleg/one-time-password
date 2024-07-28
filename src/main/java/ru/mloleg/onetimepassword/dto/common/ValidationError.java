package ru.mloleg.onetimepassword.dto.common;

import lombok.Builder;

@Builder
public record ValidationError(String field, String message) {
}
