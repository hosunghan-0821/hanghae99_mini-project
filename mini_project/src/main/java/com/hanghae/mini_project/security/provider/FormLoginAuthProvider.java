package com.hanghae.mini_project.security.provider;


import com.hanghae.mini_project.security.UserDetailsImpl;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.Resource;

public class FormLoginAuthProvider implements AuthenticationProvider {


    @Resource(name="userDetailsServiceImpl")
    private UserDetailsService userDetailsService;

    private final BCryptPasswordEncoder passwordEncoder;

    public FormLoginAuthProvider(BCryptPasswordEncoder passwordEncoder){

        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
        //FormLogin filter 로 부터 생성된 토큰으로부터 아이디와 비밀번호 조회함

        String username = token.getName();
        String password = (String) token.getCredentials();


        UserDetailsImpl userDetails =(UserDetailsImpl) userDetailsService.loadUserByUsername(username);

        if(!passwordEncoder.matches(password,userDetails.getPassword())){
            System.out.println("비밀번호 틀림");
            throw new BadCredentialsException(userDetails.getUsername() + "Invalid password");
        }

        return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {

        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
