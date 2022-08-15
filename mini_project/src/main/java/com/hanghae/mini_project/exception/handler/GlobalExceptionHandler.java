package com.hanghae.mini_project.exception.handler;

import com.hanghae.mini_project.exception.ErrorCode.Errorcode;
import com.hanghae.mini_project.exception.Exception.RestApiException;
import com.hanghae.mini_project.exception.response.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(RestApiException.class)
    public ResponseEntity<Object> handleCustomException(RestApiException e){
        Errorcode errorcode = e.getErrorcode();
        return handleExceptionInternal(errorcode);
    }


    private ResponseEntity<Object> handleExceptionInternal(Errorcode errorcode){
        return ResponseEntity.status(errorcode.getHttpStatus()).body(makeErrorResponse(errorcode));
    }

    private ErrorResponse makeErrorResponse(Errorcode errorcode){

        return ErrorResponse.builder()
                .code(errorcode.name())
                .message(errorcode.getMessage())
                .build();

    }
}
