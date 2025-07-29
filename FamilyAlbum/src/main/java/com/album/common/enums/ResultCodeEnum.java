package com.album.common.enums;

public enum ResultCodeEnum {
    SUCCESS("200", "Success"),

    PARAM_ERROR("400", "Parameter error"),

    SYSTEM_ERROR("500", "System error"),
    USER_EXIST_ERROR("5001", "Username already exists"),;


    public String code;
    public String msg;

    ResultCodeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
