package com.album.common.enums;

public enum ResultCodeEnum {
    SUCCESS("200", "Success"),

    PARAM_ERROR("400", "Parameter error"),

    SYSTEM_ERROR("500", "System error"),
    USER_EXIST_ERROR("5001", "Username already exists"),
    USER_ACCOUNT_ERROR("5003", "Invalid username or password"),
    USER_NOT_EXIST_ERROR("5004", "Account does not exist"),
    ;

    public String code;
    public String msg;

    ResultCodeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
