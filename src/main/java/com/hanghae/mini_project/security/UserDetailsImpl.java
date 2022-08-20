package com.hanghae.mini_project.security;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hanghae.mini_project.entity.User;
import com.hanghae.mini_project.entity.UserRoleEnum;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;


public class UserDetailsImpl implements UserDetails {

    private final User user;


    public UserDetailsImpl (User user){
        this.user=user;
    }

    public User getUser(){
        return user;
    }


    public String getRole(){
        return this.user.getRole().getAuthority();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        //User 객체로부터 역할 가져오기 Enum 형식
        UserRoleEnum role = user.getRole();

        //Enum 객체로부터 String으로 가져오기
        String authority = role.getAuthority();

        // (SimpleGrantedAuthorty extends GrantedAuthority) <<  객체 만들기
        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(authority);

        //return 조건을 맞추기 위해서 만들어주는 것으로 생각.
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(simpleGrantedAuthority);

        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
