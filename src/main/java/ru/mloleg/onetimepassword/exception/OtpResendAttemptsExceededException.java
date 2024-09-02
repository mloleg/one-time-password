package ru.mloleg.onetimepassword.exception;

public class OtpResendAttemptsExceededException extends OtpException {

    public OtpResendAttemptsExceededException(String message) {
        super(message);
    }
}
