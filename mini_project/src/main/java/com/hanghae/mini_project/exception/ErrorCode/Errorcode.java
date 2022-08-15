package com.hanghae.mini_project.exception.ErrorCode;

import org.springframework.http.HttpStatus;

public interface Errorcode {

    String name();
    HttpStatus getHttpStatus();
    String getMessage();
}
