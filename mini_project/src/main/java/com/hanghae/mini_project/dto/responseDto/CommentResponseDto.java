package com.hanghae.mini_project.dto.responseDto;

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
}
