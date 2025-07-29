package com.album.exception;


import com.album.common.Result;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice(basePackages="com.example.controller")
@Slf4j
public class GlobalExceptionHandler {

    //Unified exception handling with @ExceptionHandler,
    // mainly used for Exception.
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result error(HttpServletRequest request, Exception e){
        log.error("Exception : {}", e.getMessage());
        return Result.error();
    }

    @ExceptionHandler(CustomerException.class)
    @ResponseBody
    public Result customerError(HttpServletRequest request, CustomerException e){
        return Result.error(e.getCode(), e.getMsg());
    }
}
