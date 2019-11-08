package com.jay.tassadar.controller;

import com.jay.tassadar.exception.BusinessException;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@ControllerAdvice
public class ErrorHandlerController implements ErrorController {
    @Override
    public String getErrorPath() {
        return "/error";
    }

    @ExceptionHandler(value = BusinessException.class)
    public Object handleBusinessException(HttpServletRequest request, BusinessException exception) {
        return exception.getMessage();
    }
}
