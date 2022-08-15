package com.hanghae.mini_project.exception.Exception;

import com.hanghae.mini_project.exception.ErrorCode.Errorcode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RestApiException extends  RuntimeException {
    private final Errorcode errorcode;
}
