package com.hanghae.mini_project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResponseDto<T> {
    private boolean success;
    private T data;
    private Error error;

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class Error {
        private String error;
        private String message;
    }

    public static <T> ResponseDto<T> success(T data) {
        return new ResponseDto<>(true, data, null);
    }

    private static <T> ResponseDto<T> fail(String error, String message) {
        return new ResponseDto<>(false, null, new Error(error, message));
    }
}
