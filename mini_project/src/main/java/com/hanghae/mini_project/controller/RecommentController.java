package com.hanghae.mini_project.controller;

import com.hanghae.mini_project.dto.requestDto.RecommentRequestDto;
import com.hanghae.mini_project.dto.responseDto.RecommentResponseDto;
import com.hanghae.mini_project.dto.responseDto.ResponseDto;
import com.hanghae.mini_project.security.UserDetailsImpl;
import com.hanghae.mini_project.service.RecommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class RecommentController {

    private final RecommentService recommentService;

    @Secured("ROLE_RECRUITER")
    @PostMapping("/api/v1/auth/recruits/comments/{commentId}")
    public ResponseEntity<ResponseDto<?>> addRecomment(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long commentId, @RequestBody RecommentRequestDto requestDto){
        RecommentResponseDto recommentResponseDto=recommentService.saveRecomment(userDetails,requestDto,commentId);
        ResponseDto<RecommentResponseDto> responseDto = ResponseDto.success("답변 작성하였습니다",recommentResponseDto);
        return new ResponseEntity<>(responseDto,HttpStatus.OK);
    }

    @Secured("ROLE_RECRUITER")
    @PutMapping("/api/v1/auth/recruits/comments/{commentId}")
    public ResponseEntity<ResponseDto<?>> updateRecomment(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long commentId,
            @RequestBody RecommentRequestDto requestDto){

        RecommentResponseDto recommentResponseDto =recommentService.updateRecomment(userDetails,requestDto,commentId);
        return new ResponseEntity<>(ResponseDto.success("수정사항 변경되었습니다",recommentResponseDto),HttpStatus.OK);
    }

    @Secured("ROLE_RECRUITER")
    @DeleteMapping("/api/v1/auth/recruits/comments/{commentId}")
    public ResponseEntity<ResponseDto<?>> deleteRecomment(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long commentId,
            @RequestBody RecommentRequestDto requestDto){

        RecommentResponseDto recommentResponseDto =recommentService.deleteRecomment(userDetails,requestDto,commentId);
        return new ResponseEntity<>(ResponseDto.success("답변 삭제되었습니다",recommentResponseDto),HttpStatus.OK);

    }
}
