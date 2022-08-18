package com.hanghae.mini_project.dto.requestDto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@ApiModel(value="문의사항 작성 DTO_MODEL")
@Getter
public class CommentRequestDto {

    @ApiModelProperty(value = "문의사항" , example = "문의사항 작성")
    private String content;
    @ApiModelProperty(value = "댓글 고유 ID" , example = "1")
    private Long commentId;
}
