package ru.mloleg.onetimepassword.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.mloleg.onetimepassword.dto.common.CommonResponse;
import ru.mloleg.onetimepassword.dto.common.ValidationError;

import java.util.List;

@Slf4j
@RestControllerAdvice
public class OTPExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public CommonResponse<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();

        List<FieldError> fieldErrors = bindingResult.getFieldErrors();

        List<ValidationError> validationErrors = fieldErrors
            .stream()
            .map(validationError ->
                ValidationError.builder()
                               .field(validationError.getField())
                               .message(validationError.getDefaultMessage())
                               .build())
            .toList();

        log.error("Ошибка валидации ответа: {}", validationErrors, e);

        return CommonResponse.builder()
                             .errorMessage("Ошибка валидации")
                             .validationErrorList(validationErrors)
                             .build();
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public CommonResponse<?> handleException(Exception e) {
        log.error("Произошла ошибка: {}", e.getMessage(), e);

        return CommonResponse.builder()
                             .errorMessage("Произошла ошибка: {}" + e.getMessage())
                             .build();
    }
}
