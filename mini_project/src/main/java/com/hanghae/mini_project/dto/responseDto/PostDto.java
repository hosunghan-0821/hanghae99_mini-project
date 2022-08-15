package com.hanghae.mini_project.dto.responseDto;


import com.hanghae.mini_project.entity.Comment;
import com.hanghae.mini_project.entity.Post;
import com.hanghae.mini_project.entity.TechStack;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

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

        List<Comment> comments = post.getCommentList();
        for (Comment comment : comments) {
            this.commentList.add(new CommentDto(comment));
        }

        List<TechStack> techStackList = post.getTechStackList();
        for(TechStack stack:techStackList) {
            this.stackList.add(stack.getStackName());
        }
    }
    public  PostDto (Post post,String allPost){

        this.jobTitle = post.getJobTitle();
        this.id = post.getId();
        this.createdAt = post.getCreatedAt();
        this.updatedAt = post.getModifiedAt();
        this.description = post.getDescription();
        this.user = new UserDto(post.getUser());

        List<TechStack> techStackList = post.getTechStackList();
        for(TechStack stack:techStackList) {
            this.stackList.add(stack.getStackName());
        }
    }


}
