package com.example.demo.common.exception;

/**
 * 数据库异常
 */
@SuppressWarnings("serial")
public class DBException extends BaseException {

    public DBException(String message) {
        super(message);
    }
    
    public DBException(Throwable cause) {
        super(cause);
    }

    public DBException(String message, Throwable cause) {
        super(message, cause);
    }

}
