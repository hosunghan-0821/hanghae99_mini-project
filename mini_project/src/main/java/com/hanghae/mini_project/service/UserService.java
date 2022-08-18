package com.hanghae.mini_project.service;


import com.hanghae.mini_project.dto.requestDto.SignupRequestDto;
import com.hanghae.mini_project.dto.responseDto.LoginInfoDto;
import com.hanghae.mini_project.dto.responseDto.ResponseDto;
import com.hanghae.mini_project.entity.User;
import com.hanghae.mini_project.entity.UserRoleEnum;
import com.hanghae.mini_project.exception.ErrorCode.CommonErrorCode;
import com.hanghae.mini_project.exception.ErrorCode.CustomErrorCode;
import com.hanghae.mini_project.exception.Exception.RestApiException;
import com.hanghae.mini_project.repository.UserRepository;
import com.hanghae.mini_project.security.jwt.HeaderTokenExtractor;
import com.hanghae.mini_project.security.jwt.JwtDecoder;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UserService {


    private final UserRepository userRepository;

    private final JwtDecoder jwtDecoder;
    private final PasswordEncoder passwordEncoder;

    private final HeaderTokenExtractor headerTokenExtractor;

    @Resource(name="userDetailsServiceImpl")
    private UserDetailsService userDetailsService;

    public ResponseDto<?> registerUser(SignupRequestDto requestDto){

        //여기서 요구조건 확인하는
        if(!checkSignupValueCondition(requestDto)){
            throw new RestApiException(CommonErrorCode.INVALID_PARAMETER);
        };
        //회원 닉네임 중복 확인
        Optional<User> found = userRepository.findByUsername(requestDto.getUsername());
        if(found.isPresent()){
            throw new RestApiException(CustomErrorCode.DUPLICATE_RESOURCE);
        }
        requestDto.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        UserRoleEnum role;
        if(requestDto.getAuthority().equals(UserRoleEnum.Authority.JOB_SEEKER_STR)){
            role = UserRoleEnum.JOB_SEEKER;
        }
        else{
            role = UserRoleEnum.RECRUITER;
        }
        User userInfo = new User(requestDto,role);
        userRepository.save(userInfo);

        LoginInfoDto loginInfoDto = LoginInfoDto.builder()
                .username(userInfo.getUsername())
                .authority(userInfo.getRole().getAuthority())
                .build();
        //ResponseDto 만들기
       return  ResponseDto.success("회원가입에 성공하였습니다.",loginInfoDto);

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
