package com.hzy.security.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * User: hzy
 * Date: 2023/5/19
 * Time: 22:22
 * Description:
 */
@RestControllerAdvice
public class ErrorHandler {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public Map<String, Object> handle(Exception exception) {
        Map<String, Object> info = new HashMap<>();
        info.put("message", exception.getMessage());
        info.put("time", new Date().getTime());
        return info;
    }
}
