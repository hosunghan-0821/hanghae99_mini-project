package com.hanghae.mini_project.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hanghae.mini_project.dto.requestDto.SignupRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@Entity
@Table (name="users")
@NoArgsConstructor
public class User extends Timestamped {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column
    private String username;

    @JsonIgnore
    @Column(nullable = false)
    private String password;

    @Column
    private String companyName;

    @Column
    private String websiteUrl;

    @Column
    private String contactNum;

    @Column
    private String profileImageUrl;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;


    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, mappedBy="user")
    private List<PostLike> postLikes = new ArrayList<>();

    public User(SignupRequestDto requestDto,UserRoleEnum role){
        //권한에 대해서 만들어야함.
        this.username = requestDto.getUsername();
        this.password = requestDto.getPassword();
        this.companyName = requestDto.getCompanyName();
        this.profileImageUrl = requestDto.getProfileImageUrl();
        this.role=role;
    }

    public void cancelInterest(PostLike postLike) {
        this.postLikes.remove(postLike);
    }

    public void addInterest(PostLike postLike) {
        this.postLikes.add(postLike);
    }
}
