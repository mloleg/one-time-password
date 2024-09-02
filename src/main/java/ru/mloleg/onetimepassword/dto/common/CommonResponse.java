package ru.mloleg.onetimepassword.dto.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.util.List;

@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record CommonResponse<T>(T body,
                                String errorMessage,
                                List<ValidationError> validationErrorList) {

}
