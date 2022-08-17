package com.hanghae.mini_project.controller;

import com.hanghae.mini_project.dto.requestDto.CommentRequestDto;
import com.hanghae.mini_project.dto.responseDto.CommentResponseDto;
import com.hanghae.mini_project.dto.responseDto.ResponseDto;
import com.hanghae.mini_project.security.UserDetailsImpl;
import com.hanghae.mini_project.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @Secured("ROLE_JOB_SEEKER")
    @PostMapping("/api/v1/auth/recruits/{id}/comments")
    public ResponseEntity<ResponseDto<?>> addComment(@PathVariable(value = "id") Long postId,
                                                     @RequestBody CommentRequestDto commentRequestDto,
                                                     @AuthenticationPrincipal UserDetailsImpl userDetails) {
        CommentResponseDto commentResponseDto = commentService.saveComment(postId, commentRequestDto, userDetails);
        return new ResponseEntity<>(ResponseDto.success("문의사항을 작성하였습니다.",commentResponseDto), HttpStatus.OK);
    }


    @Secured("ROLE_JOB_SEEKER")
    @PutMapping("/api/v1/auth/recruits/{id}/comments")
    public ResponseEntity<ResponseDto<?>> updateComment(@PathVariable(value = "id") Long postId,
                                                        @RequestBody CommentRequestDto commentRequestDto,
                                                        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        CommentResponseDto commentResponseDto = commentService.updateComment(postId, commentRequestDto, userDetails);
        return new ResponseEntity<>(ResponseDto.success("문의사항 수정이 완료되었습니다.", commentResponseDto), HttpStatus.OK);
    }

    @Secured("ROLE_JOB_SEEKER")
    @DeleteMapping("api/v1/auth/recruits/{id}/comments")
    public ResponseEntity<ResponseDto<?>> deleteComment(@PathVariable(value = "id") Long postId,
                                                        @RequestBody CommentRequestDto commentRequestDto,
                                                        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        CommentResponseDto commentResponseDto = commentService.deleteComment(postId, commentRequestDto, userDetails);
        return new ResponseEntity<>(ResponseDto.success("문의사항이 삭제되었습니다.", commentResponseDto), HttpStatus.OK);
    }
}
