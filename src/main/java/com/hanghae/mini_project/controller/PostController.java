package com.hanghae.mini_project.controller;


import com.hanghae.mini_project.dto.responseDto.PostResponse;
import com.hanghae.mini_project.service.PostService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }





    @GetMapping("/api/v1/posts/{id}")
    public PostResponse getPost(@PathVariable Long id) {

        PostResponse post = postService.getPost(id);
        return post;
    }
}
