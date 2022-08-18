package com.hanghae.mini_project.dto.responseDto;

import com.hanghae.mini_project.entity.Recomment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class RecommentDto {
    private Long id;
    private String username;
    private String content;
    private String createdAt;
    private String modifiedAt;

    private String profileImageUrl;

    public RecommentDto(Recomment recomment){
        this.id = recomment.getId();
        this.content = recomment.getContent();
        this.createdAt = recomment.getCreatedAt();
        this.modifiedAt = recomment.getModifiedAt();
        this.username = recomment.getUser().getUsername();
        this.profileImageUrl = recomment.getUser().getProfileImageUrl();
    }
}
