package com.hanghae.mini_project.dto.requestDto.postReqDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TechStackDto {
    private List<String> stackList;
}
