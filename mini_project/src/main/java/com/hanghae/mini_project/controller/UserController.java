package com.hanghae.mini_project.controller;

import com.hanghae.mini_project.dto.requestDto.SignupRequestDto;
import com.hanghae.mini_project.dto.responseDto.ResponseDto;
import com.hanghae.mini_project.security.UserDetailsImpl;
import com.hanghae.mini_project.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
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


    //참조해서 사용하세요~
    @Secured("ROLE_RECRUITER")//사용가능한 권한 명시
    @GetMapping("/api/v1/hello")// URL
    public ResponseEntity<ResponseDto<?>> hello(){
        return  new ResponseEntity<>(ResponseDto.success("성공","DTO_객체넣으세요"),HttpStatus.OK);
    }
}
