package com.hanghae.mini_project.dto.requestDto.postReqDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostRequestDto {

    private String jobTitle;
    private TechStackDto techStackList;
    private String description;
}
