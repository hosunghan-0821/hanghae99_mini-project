package com.hanghae.mini_project.dto.responseDto;

import com.hanghae.mini_project.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class LoginInfoDto {

    private boolean success;
    private Object data;
    private String error;

    public LoginInfoDto(){
        success=true;
        this.data="로그인이 완료되었습니다.";
        this.error=null;
    }
    public LoginInfoDto(User user){
        this.success=true;
        this.data="로그인이 완료되었습니다.";
        this.error=null;
    }
}
