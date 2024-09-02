package ru.mloleg.onetimepassword.exception;

public class ResponseTimeoutException extends OtpException {

    public ResponseTimeoutException(String message) {
        super(message);
    }
}
