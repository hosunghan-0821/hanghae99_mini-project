package com.hanghae.mini_project.dto.responseDto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecommentResponseDto {
    private Long id;
    private String username;
    private String content;
    private String createdAt;
    private String modifiedAt;
}
