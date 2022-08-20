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
import java.util.Optional;
import java.util.regex.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public ResponseDto<?> registerUser(SignupRequestDto requestDto){
        checkSignupValueCondition(requestDto);

        Optional<User> found = userRepository.findByUsername(requestDto.getUsername());
        if(found.isPresent()){
            throw new RestApiException(CustomErrorCode.DUPLICATE_RESOURCE);
        }

        UserRoleEnum role;
        if(requestDto.getAuthority().equals(UserRoleEnum.Authority.JOB_SEEKER_STR)){
            role = UserRoleEnum.JOB_SEEKER;
        }
        else{
            role = UserRoleEnum.RECRUITER;
        }
        User userInfo = new User(requestDto, passwordEncoder.encode(requestDto.getPassword()), role);
        userRepository.save(userInfo);

        LoginInfoDto loginInfoDto = LoginInfoDto.builder()
                .username(userInfo.getUsername())
                .authority(userInfo.getRole().getAuthority())
                .build();

       return  ResponseDto.success("회원가입에 성공하였습니다.",loginInfoDto);

    }

    public void checkSignupValueCondition(SignupRequestDto requestDto){

        String Id = requestDto.getUsername();
        String pw = requestDto.getPassword();
        String pwCheck = requestDto.getPasswordConfirm();

        String pattern = "^[a-zA-Z0-9]*$";

        if( !(Pattern.matches(pattern,Id) && Id.length()>=4 && Id.length()<=20) ){
            throw new RestApiException(CommonErrorCode.INVALID_PARAMETER);
        }

        if( !(Pattern.matches(pattern,pw) && pw.length()>=4 && pw.length()<=32) ){
            throw new RestApiException(CommonErrorCode.INVALID_PARAMETER);
        }

        if( !pw.equals(pwCheck) ){
            throw new RestApiException(CommonErrorCode.INVALID_PARAMETER);
        }
    }
}
