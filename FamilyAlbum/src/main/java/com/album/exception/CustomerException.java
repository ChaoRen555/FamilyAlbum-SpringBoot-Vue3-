package com.album.exception;

import com.album.common.enums.ResultCodeEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CustomerException extends RuntimeException {
    private String code;
    private String msg;

    public CustomerException(ResultCodeEnum resultCodeEnum) {
        this.code = resultCodeEnum.code;
        this.msg = resultCodeEnum.msg;
    }

    public CustomerException(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
