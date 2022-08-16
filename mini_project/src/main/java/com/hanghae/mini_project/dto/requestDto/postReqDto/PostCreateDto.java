package com.hanghae.mini_project.dto.requestDto.postReqDto;

import com.hanghae.mini_project.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PostCreateDto {
    private String jobTitle;
    private String description;
    private User user;
}
