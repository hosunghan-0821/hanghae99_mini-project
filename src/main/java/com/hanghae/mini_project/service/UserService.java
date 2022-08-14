package com.hanghae.mini_project.service;


import com.hanghae.mini_project.dto.requestDto.SignupRequestDto;
import com.hanghae.mini_project.dto.responseDto.LoginInfoDto;
import com.hanghae.mini_project.entity.User;
import com.hanghae.mini_project.repository.UserRepository;
import com.hanghae.mini_project.security.jwt.HeaderTokenExtractor;
import com.hanghae.mini_project.security.jwt.JwtDecoder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Getter
@Service
@RequiredArgsConstructor
public class UserService {


    private final UserRepository userRepository;

    private final JwtDecoder jwtDecoder;
    private final PasswordEncoder passwordEncoder;

    private final HeaderTokenExtractor headerTokenExtractor;

    @Resource(name = "userDetailsServiceImpl")
    private UserDetailsService userDetailsService;

    private String userName;
    private String companyName;
    private String contactNum;
    private String ProfileImageUrl;

    //진무----------------
    User user = new User(userName, companyName, contactNum, ProfileImageUrl);
    //--------------------



    public void registerUser(SignupRequestDto requestDto){

        //여기서 요구조건 확인하는
        if(!checkSignupValueCondition(requestDto)){
            throw new IllegalArgumentException("회원가입 정보가 정확하지 않습니다.");
        }
        //회원 닉네임 중복 확인
        Optional<User> found = userRepository.findByUsername(requestDto.getUsername());
        if(found.isPresent()){
            throw new IllegalArgumentException("중복된 사용자 id가 존재합니다.");
        }
        requestDto.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        User userInfo = new User(requestDto);
        userRepository.save(userInfo);


    }

    public boolean checkSignupValueCondition(SignupRequestDto requestDto){

        String Id = requestDto.getUsername();
        String pw = requestDto.getPassword();
        String pwCheck = requestDto.getPasswordConfirm();

        boolean checkValueCondition = true;

        String pattern = "^[a-zA-Z0-9]*$";

        if( !(Pattern.matches(pattern,Id) && Id.length()>=4 && Id.length()<=20) ){
            System.out.println("닉네임 문제");
            checkValueCondition=false;
        }
        else if( !(Pattern.matches(pattern,pw) && pw.length()>=4 && pw.length()<=32) ){
            System.out.println("비번 문제");
            checkValueCondition=false;
        }
        else if( !pw.equals(pwCheck) ){
            System.out.println("비번 중복 문제");
            checkValueCondition=false;
        }
        return checkValueCondition;
    }


}
