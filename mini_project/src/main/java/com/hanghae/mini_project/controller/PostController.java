package com.hanghae.mini_project.controller;

import com.hanghae.mini_project.dto.requestDto.postReqDto.PostRequestDto;
import com.hanghae.mini_project.security.UserDetailsImpl;
import com.hanghae.mini_project.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/api/v1/posts")
    public ResponseEntity<?> getAllPosts() {
        return postService.getAllPosts();
    }

    @GetMapping("/api/v1/posts/{id}")
    public ResponseEntity<?> getPostDetail(@PathVariable Long id) {
        return postService.getPostDetail(id);
    }

    @Secured("ROLE_RECRUITER")
    @PostMapping("/api/v1/auth/recruits")
    public ResponseEntity<?> createPost(@RequestBody PostRequestDto postRequestDto,
                           @AuthenticationPrincipal UserDetailsImpl userDetails){

        return postService.createPost(postRequestDto, userDetails);
    }

    @Secured("ROLE_RECRUITER")
    @PutMapping("/api/v1/auth/recruits/{id}")
    public ResponseEntity<?> updatePost(@PathVariable Long id,
                                     @RequestBody PostRequestDto postRequestDto,
                                     @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.update(id, postRequestDto, userDetails);
    }


    @Secured("ROLE_RECRUITER")
    @DeleteMapping("/api/v1/auth/recruits/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id,
                                        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.deletePost(id, userDetails);
    }


    @GetMapping("/api/v1/auth/recruits/interest/{id}")
    public ResponseEntity<?> addInterestPost(@PathVariable Long id,
                                             @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.addInterestPosts(id, userDetails);
    }


}
