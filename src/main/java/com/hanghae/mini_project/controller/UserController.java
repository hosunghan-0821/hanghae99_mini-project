package com.hanghae.mini_project.controller;

import com.hanghae.mini_project.dto.requestDto.SignupRequestDto;
import com.hanghae.mini_project.dto.responseDto.LoginInfoDto;
import com.hanghae.mini_project.security.UserDetailsImpl;
import com.hanghae.mini_project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService=userService;
    }


    @PostMapping("/api/v1/signup")
    @ResponseBody
    public SignupRequestDto registerUser(@RequestBody SignupRequestDto signupRequestDto){
        System.out.println(signupRequestDto.getUsername()+"\n"+signupRequestDto.getPassword());
        userService.registerUser(signupRequestDto);
        return signupRequestDto;
    }
    // 진무
    // 각 조회
    @GetMapping("/api/v1/signup/{id}")
    public LoginInfoDto getUser(@PathVariable Long id) {
        LoginInfoDto user = userService.getUser(id);
        return user;
    }
}
