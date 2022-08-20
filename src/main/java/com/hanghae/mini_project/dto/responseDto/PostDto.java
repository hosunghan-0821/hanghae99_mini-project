package com.hanghae.mini_project.dto.responseDto;


import static java.util.stream.Collectors.toList;

import com.hanghae.mini_project.entity.Comment;
import com.hanghae.mini_project.entity.Post;
import com.hanghae.mini_project.entity.TechStack;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class PostDto {
    private Long id;
    private String createdAt;
    private String updatedAt;
    private String description;
    private UserDto user;

    private String jobTitle;
    private List<CommentDto> commentList= new ArrayList<>();
    private List<String> stackList = new ArrayList<>();

    public PostDto(Post post) {
        this.id = post.getId();
        this.createdAt = post.getCreatedAt();
        this.updatedAt = post.getModifiedAt();
        this.description = post.getDescription();
        this.user = new UserDto(post.getUser());
        this.jobTitle = post.getJobTitle();
    }

    public void updateComments(List<Comment> comments) {
        commentList = commentList.stream()
            .map(CommentDto::new)
            .collect(toList());
    }

    public void updateStackList(List<TechStack> techStackList) {
        stackList = techStackList.stream()
            .map(TechStack::getStackName)
            .collect(toList());
    }
}
