package ru.mloleg.onetimepassword.exception;

public class OtpTtlExceededException extends OtpException {

    public OtpTtlExceededException(String message) {
        super(message);
    }
}
