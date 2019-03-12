package com.example.demo.common;

public enum ResultCode {

    /**
     * 成功. ErrorCode : 0
     */
    SUCCESS("0", "成功"),
    /**
     * 未知异常. ErrorCode : 01
     */
    UnknownException("01", "未知异常"),
    /**
     * 系统异常. ErrorCode : 02
     */
    SystemException("02", "系统异常"),
    /**
     * 数据库操作异常. ErrorCode : 03
     */
    DBException("03", "数据库操作异常"),
    /**
     * 业务错误. ErrorCode : 04
     */
    BusinessException("04", "业务错误"),
    /**
     * 提示错误. ErrorCode : 05
     */
    InfoException("05", "提示错误"),
    /**
     * 参数错误. ErrorCode : 06
     */
    ParamException("06", "参数错误"),
    /**
     * 维护错误. ErrorCode : 11
     */
    MaintainException("11", "系统正在维护");

    private String _code;
    private String _message;

    private ResultCode(String code, String message) {
        _code = code;
        _message = message;
    }

    public String getCode() {
        return _code;
    }

    public String getMessage() {
        return _message;
    }

    public static ResultCode getByCode(String code) {
        for (ResultCode ec : ResultCode.values()) {
            if (ec.getCode().equals(code)) {
                return ec;
            }
        }
        return null;
    }
    
}
