package com.newgen.API.BlogApp.exception;

import com.newgen.API.BlogApp.payload.ErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    //Specific Exception ----
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails> HandleResourceNotFoundException(
            ResourceNotFoundException exception, WebRequest webRequest ){
        ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    //Globle Exception -------
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> HandleGlobalException(
            Exception exception, WebRequest webRequest ){
        ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
