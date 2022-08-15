package com.hanghae.mini_project.security;


import com.hanghae.mini_project.security.filter.FormLoginFilter;
import com.hanghae.mini_project.security.filter.JwtAuthFilter;
import com.hanghae.mini_project.security.handler.AccessDeniedHandler;
import com.hanghae.mini_project.security.handler.AuthenticationFailHandler;
import com.hanghae.mini_project.security.handler.FormLoginFailureHandler;
import com.hanghae.mini_project.security.handler.FormLoginSuccessHandler;
import com.hanghae.mini_project.security.jwt.HeaderTokenExtractor;
import com.hanghae.mini_project.security.provider.FormLoginAuthProvider;
import com.hanghae.mini_project.security.provider.JWTAuthProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(securedEnabled = true) // @Secured 어노테이션 활성화
public class WebSecurityConfig {

    private final JWTAuthProvider jwtAuthProvider;
    private final AuthenticationConfiguration authenticationConfiguration;
    private final HeaderTokenExtractor headerTokenExtractor;
    private final FormLoginSuccessHandler formLoginSuccessHandler;


    private final FormLoginFailureHandler formLoginFailureHandler;

    private final AccessDeniedHandler accessDeniedHandler;

    private final AuthenticationFailHandler authenticationFailHandler;


    @Bean
    public BCryptPasswordEncoder encodePassword() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain( HttpSecurity http, AuthenticationManagerBuilder auth) throws Exception {
        //인증 (Authentication)**: 사용자 신원을 확인하는 행위
        //인가 (Authorization)**: 사용자 권한을 확인하는 행위
        auth
                .authenticationProvider(jwtAuthProvider)
                .authenticationProvider(formLoginAuthProvider());

        http.csrf().disable();
        http.cors().configurationSource(corsConfigurationSource());

        http
                .addFilterBefore(formLoginFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);

        http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http
                .authorizeRequests()
                .anyRequest()
                .permitAll()
                .and()
                //로그아웃 기능 허용
                .logout()
                .logoutUrl("/api/logout")
                .logoutSuccessUrl("/")
                .permitAll()
                .and()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    //사용하는 필터 만들기
    @Bean
    public FormLoginFilter formLoginFilter() throws Exception {
        FormLoginFilter formLoginFilter = new FormLoginFilter(authenticationManager(authenticationConfiguration));
        formLoginFilter.setFilterProcessesUrl("/api/v1/login");
        formLoginFilter.setAuthenticationFailureHandler(formLoginFailureHandler);
        formLoginFilter.setAuthenticationSuccessHandler(formLoginSuccessHandler);
        formLoginFilter.afterPropertiesSet();
//        System.out.println(authenticationManager(authenticationConfiguration));
        return formLoginFilter;
    }

    //사용하는 필터 만들기
    private JwtAuthFilter jwtFilter() throws Exception {

        List<String> skipPathList = new ArrayList<>();

        // Static 정보 접근 허용
        skipPathList.add("GET,/CSS/**");
        skipPathList.add("GET,/JS/**");

        // 회원 관리 API SKIP 적용
        skipPathList.add("POST,/api/v1/signup");

        // Post 게시글 관련
        skipPathList.add("GET,/api/v1/posts/**");

        //기본 페이지 설정
        skipPathList.add("GET,/");
        skipPathList.add("GET,/favicon.ico");

        FilterSkipMatcher matcher = new FilterSkipMatcher(skipPathList, "/**");
        JwtAuthFilter filter = new JwtAuthFilter(headerTokenExtractor, matcher);

        filter.setAuthenticationFailureHandler(authenticationFailHandler);
        filter.setAuthenticationManager(authenticationManager(authenticationConfiguration));
        return filter;
    }

    @Bean
    public FormLoginAuthProvider formLoginAuthProvider() {
        return new FormLoginAuthProvider(encodePassword());
    }


    //cors 허용 적용
    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.addAllowedOrigin("http://localhost:3000");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",configuration);
        return source;
    }
}
