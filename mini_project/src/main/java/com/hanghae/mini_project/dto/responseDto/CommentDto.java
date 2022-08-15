package com.hanghae.mini_project.dto.responseDto;

import com.hanghae.mini_project.entity.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentDto {
    private String createdAt;
    private UserDto user;
    private String content;

    public CommentDto(Comment comment) {
        this.createdAt = comment.getCreatedAt();
        this.user = new UserDto(comment.getUser());
        this.content = comment.getContent();
    }
}
