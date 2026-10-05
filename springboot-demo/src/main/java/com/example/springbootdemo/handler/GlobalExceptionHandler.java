package com.example.springbootdemo.handler;

import com.example.springbootdemo.common.Result;
import com.example.springbootdemo.exception.BusinessException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.MethodArgumentNotValidException;
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 处理业务异常
    @ExceptionHandler(BusinessException.class)
    public Result<Void> handleBusinessException(BusinessException e) {
        return Result.error(e.getCode(), e.getMessage());
    }
    // 新增：处理参数校验异常
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<Void> handleValidException(MethodArgumentNotValidException e) {
        // 取出第一个校验错误消息
        String message = e.getBindingResult().getFieldError().getDefaultMessage();
        return Result.error(400, message);
    }
    // 处理其他所有异常
    @ExceptionHandler(Exception.class)
    public Result<Void> handleException(Exception e) {
        return Result.error(500, "服务器内部错误：" + e.getMessage());
    }
}

