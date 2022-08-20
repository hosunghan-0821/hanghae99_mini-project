package com.hanghae.mini_project.dto.responseDto;

import com.hanghae.mini_project.entity.Comment;
import com.hanghae.mini_project.entity.Recomment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CommentDto {
    private String createdAt;
    private UserDto user;
    private String content;
    private Long id;
    private List<RecommentDto> recommentList = new ArrayList<>();

    public CommentDto(Comment comment) {
        this.id = comment.getId();
        this.createdAt = comment.getCreatedAt();
        this.user = new UserDto(comment.getUser());
        this.content = comment.getContent();


        List<Recomment> recomments = comment.getRecommentList();

        for(int i = 0 ; i<recomments.size();i++){
            this.recommentList.add(new RecommentDto(recomments.get(i)));
        }
    }

    public CommentDto(CommentDto commentDto) {
    }
}
