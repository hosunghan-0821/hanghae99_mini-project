package com.hanghae.mini_project.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hanghae.mini_project.dto.requestDto.postReqDto.PostLikeDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class PostLike {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;


    public PostLike(PostLikeDto postLikeDto) {
        this.user = postLikeDto.getUser();
        this.post = postLikeDto.getPost();
    }

}
