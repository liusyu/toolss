package com.example.demo.common.exception;

@SuppressWarnings("serial")
public class SystemException extends BaseException {

    public SystemException(String message) {
        super(message);
    }

    public SystemException(Throwable cause) {
        super(cause);
    }

    public SystemException(String message, Throwable cause) {
        super(message, cause);
    }

}
