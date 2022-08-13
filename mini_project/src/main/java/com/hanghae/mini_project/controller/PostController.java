package com.hanghae.mini_project.controller;

import com.hanghae.mini_project.dto.requestDto.postReqDto.PostRequestDto;
import com.hanghae.mini_project.security.UserDetailsImpl;
import com.hanghae.mini_project.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth/recruits")
public class PostController {

    private final PostService postService;

    @PostMapping("/")
    public ResponseEntity<?> createPost(@RequestBody PostRequestDto postRequestDto,
                           @AuthenticationPrincipal UserDetailsImpl userDetails){

        return new ResponseEntity<>(postService.createPost(postRequestDto, userDetails),HttpStatus.OK);
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> updatePost(@PathVariable Long id,
                                     @RequestBody PostRequestDto postRequestDto,
                                     @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.update(id, postRequestDto, userDetails);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id,
                                        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.deletePost(id, userDetails);
    }


}
