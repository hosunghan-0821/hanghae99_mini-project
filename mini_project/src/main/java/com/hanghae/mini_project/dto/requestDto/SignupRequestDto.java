package com.hanghae.mini_project.dto.requestDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequestDto {
    private String username;
    private String password;
    private String passwordConfirm;
    private String companyName;
    private String authority;
    private String profileImageUrl;
    private String websiteUrl;
}
