package com.hanghae.mini_project.dto.requestDto.postReqDto;

import com.hanghae.mini_project.entity.Post;
import com.hanghae.mini_project.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostLikeDto {
    private User user;
    private Post post;
}
