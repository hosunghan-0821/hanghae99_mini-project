package com.hanghae.mini_project.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanghae.mini_project.dto.responseDto.ResponseDto;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AccessDeniedHandler implements org.springframework.security.web.access.AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        //System.out.println(accessDeniedException.getMessage());
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(
                new ObjectMapper().writeValueAsString(
                        ResponseDto.fail("AUTHORIZE_FAIL", accessDeniedException.getMessage())
                )
        );
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }
}
