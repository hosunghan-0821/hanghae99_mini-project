package com.hanghae.mini_project.exception.ErrorCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum CustomErrorCode implements  Errorcode{
    UNAUTHORIZED_REQUEST(HttpStatus.UNAUTHORIZED,"작성자만 문의-답변 기능의 이용이 허용됩니다.")
    ;
    private final HttpStatus httpStatus;
    private final String message;

}
