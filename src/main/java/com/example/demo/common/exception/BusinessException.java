package com.example.demo.common.exception;

/**
 * 业务异常的自定义封装类
 */
@SuppressWarnings("serial")
public class BusinessException extends BaseException {

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

}
