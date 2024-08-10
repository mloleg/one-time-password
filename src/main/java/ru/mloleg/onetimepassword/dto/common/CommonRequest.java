package ru.mloleg.onetimepassword.dto.common;

import jakarta.validation.Valid;
import lombok.Builder;

@Builder
public record CommonRequest<T>(@Valid T body) {

}
