package com.hanghae.mini_project.controller;

import com.hanghae.mini_project.dto.requestDto.SignupRequestDto;
import com.hanghae.mini_project.dto.responseDto.ResponseDto;
import com.hanghae.mini_project.security.UserDetailsImpl;
import com.hanghae.mini_project.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/api/v1/signup")
    public ResponseDto<?> registerUser(@RequestBody SignupRequestDto signupRequestDto){
        return userService.registerUser(signupRequestDto);
    }
}
