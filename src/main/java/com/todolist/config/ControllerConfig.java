package com.todolist.config;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@CrossOrigin
@ControllerAdvice
public class ControllerConfig {

    @ExceptionHandler()
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handle(HttpMessageNotReadableException exception) {
        throw exception;
    }
    
    @ExceptionHandler(IOException.class) 
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE) 
    public Object exceptionHandler(IOException exception, HttpServletRequest request) { 
    	return new HttpEntity<>(exception.getMessage()); 
    }
}