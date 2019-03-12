package com.example.demo.common.exception;

@SuppressWarnings("serial")
public class InfoException extends BaseException {

    public InfoException(String message) {
        super(message);
    }

    public InfoException(Throwable cause) {
        super(cause);
    }

    public InfoException(String message, Throwable cause) {
        super(message, cause);
    }

}
