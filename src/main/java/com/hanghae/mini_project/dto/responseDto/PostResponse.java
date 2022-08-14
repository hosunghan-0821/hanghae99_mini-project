package com.hanghae.mini_project.dto.responseDto;

import com.hanghae.mini_project.controller.PostController;
import com.hanghae.mini_project.entity.Post;

public class PostResponse {

    private String description;

    public PostResponse(Post post) {
        this.description = post.getDescription();
    }

    public String getDescription() {
        return description;
    }

    public void add(PostResponse postResponse) {
    }
}
