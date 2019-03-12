package com.example.demo.common.exception;

@SuppressWarnings("serial")
public class MaintainException extends BaseException {

    public MaintainException(String message) {
        super(message);
    }

    public MaintainException(Throwable cause) {
        super(cause);
    }

    public MaintainException(String message, Throwable cause) {
        super(message, cause);
    }

}
