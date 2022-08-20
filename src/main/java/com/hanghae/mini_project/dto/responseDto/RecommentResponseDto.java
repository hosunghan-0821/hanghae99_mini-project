package com.hanghae.mini_project.dto.responseDto;

import com.hanghae.mini_project.entity.Recomment;
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
    private String profileImageUrl;

    public static RecommentResponseDto of (Recomment recomment) {
        return RecommentResponseDto.builder()
            .username(recomment.getUser().getUsername())
            .createdAt(recomment.getCreatedAt())
            .modifiedAt(recomment.getModifiedAt())
            .profileImageUrl(recomment.getUser().getProfileImageUrl())
            .id(recomment.getId())
            .content(recomment.getContent())
            .build();
    }
}
