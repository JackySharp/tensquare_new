package com.tensquare.common.exception;

import com.tensquare.common.entity.ErrorCode;
import com.tensquare.common.entity.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class MyExceptionHandler {
    @ExceptionHandler(Exception.class)//声明要处理的异常类型：全部异常

    @ResponseBody
    public static Result exceptionHandler(Exception e) {
        e.printStackTrace();
        return new Result(false,ErrorCode.ERROR,e.getMessage());
    }
}
