package com.hanghae.mini_project.dto.requestDto.postReqDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class PostRequestDto {

    private TechStackDto techStackList;
    private String description;
}
