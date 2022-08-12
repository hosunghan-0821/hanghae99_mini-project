package com.hanghae.mini_project.security;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.hanghae.mini_project.dto.responseDto.LoginInfoDto;
import com.hanghae.mini_project.security.jwt.JwtTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
public class FormLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    public static final String AUTH_HEADER = "Authorization";
    public static final String TOKEN_TYPE = "BEARER";
    public static final String AUTH_REFRESH_HEADER = "Refresh";

    private ObjectMapper mapper = new ObjectMapper();

//    private final RefreshTokenRepository refreshTokenRepository;
//
//    @Autowired
//    public FormLoginSuccessHandler(RefreshTokenRepository refreshTokenRepository){
//        this.refreshTokenRepository =refreshTokenRepository;
//    }


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {

        final UserDetailsImpl userDetails = ( (UserDetailsImpl) authentication.getPrincipal());
        final String token = JwtTokenUtils.generateJwtToken(userDetails);
//        final String refreshTokenStr = JwtTokenUtils.generateJwtRefreshToken();
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");

        // loginInfoDto 객체 생성
        LoginInfoDto loginInfoDto = new LoginInfoDto();

        // json 형태로 바꾸기
        String result = mapper.writeValueAsString(loginInfoDto);
        response.getWriter().write(result);
        response.addHeader(AUTH_HEADER,TOKEN_TYPE+" "+token);
    }
}
