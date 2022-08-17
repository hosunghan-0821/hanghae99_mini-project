package com.hanghae.mini_project.exception.ErrorCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum CustomErrorCode implements  Errorcode{
    UNAUTHORIZED_RECRUIT_REQUEST(HttpStatus.UNAUTHORIZED,"작성자만 문의-답변 허용됩니다."),
    UNAUTHORIZED_SEEKER_REQUEST(HttpStatus.UNAUTHORIZED,"문의자만 문의에 대한 권한을 갖습니다.."),
    DUPLICATE_RESOURCE(HttpStatus.BAD_REQUEST,"중복된 아이디 입니다.")
    ;
    private final HttpStatus httpStatus;
    private final String message;



}
