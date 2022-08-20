package com.hanghae.mini_project.dto.responseDto;

import com.hanghae.mini_project.entity.Comment;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentResponseDto {
    private Long id;
    private String username;
    private String content;
    private String createdAt;
    private String modifiedAt;
    private String profileImageUrl;

    public static CommentResponseDto of(Comment comment) {
        return CommentResponseDto.builder()
            .username(comment.getUser().getUsername())
            .createdAt(comment.getCreatedAt())
            .modifiedAt(comment.getModifiedAt())
            .profileImageUrl(comment.getUser().getProfileImageUrl())
            .id(comment.getId())
            .content(comment.getContent())
            .build();
    }
}
