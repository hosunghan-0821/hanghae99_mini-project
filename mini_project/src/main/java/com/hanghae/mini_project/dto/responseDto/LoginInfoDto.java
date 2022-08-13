package com.hanghae.mini_project.dto.responseDto;

import com.hanghae.mini_project.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginInfoDto {

    private String username;
    private String authority;
}
