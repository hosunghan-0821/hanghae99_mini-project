package com.hanghae.mini_project.service;

import com.hanghae.mini_project.dto.requestDto.RecommentRequestDto;
import com.hanghae.mini_project.dto.responseDto.RecommentResponseDto;
import com.hanghae.mini_project.entity.Comment;
import com.hanghae.mini_project.entity.Recomment;
import com.hanghae.mini_project.exception.ErrorCode.CommonErrorCode;
import com.hanghae.mini_project.exception.ErrorCode.CustomErrorCode;
import com.hanghae.mini_project.exception.Exception.RestApiException;
import com.hanghae.mini_project.repository.CommentRepository;
import com.hanghae.mini_project.repository.RecommentRepository;
import com.hanghae.mini_project.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RecommentService {

    private final RecommentRepository recommentRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public RecommentResponseDto saveRecomment(UserDetailsImpl userDetails, RecommentRequestDto requestDto,Long commentId){
        Comment comment = getCommentById(commentId);
        checkValidatecomment(userDetails, comment);

        Recomment recomment = Recomment.builder()
                    .user(userDetails.getUser())
                    .comment(comment)
                    .content(requestDto.getContent())
                    .build();

        recommentRepository.save(recomment);

        return RecommentResponseDto.of(recomment);
    }


    @Transactional
    public RecommentResponseDto updateRecomment(UserDetailsImpl userDetails, RecommentRequestDto requestDto, Long commentId){
        Comment comment = getCommentById(commentId);
        checkValidatecomment(userDetails, comment);

        Recomment recomment=getRecommentById(requestDto.getRecommentId());
        recomment.update(requestDto.getContent());

        return RecommentResponseDto.of(recomment);
    }


    @Transactional
    public RecommentResponseDto deleteRecomment(UserDetailsImpl userDetails, RecommentRequestDto requestDto, Long commentId){
        Comment comment = getCommentById(commentId);
        checkValidatecomment(userDetails, comment);

        Recomment recomment=getRecommentById(requestDto.getRecommentId());
        recommentRepository.delete(recomment);

        return RecommentResponseDto.of(recomment);
    }

    private Comment checkValidatecomment(UserDetailsImpl userDetails, Comment comment){
        if(!(comment.getPost().getUser().getId() == userDetails.getUser().getId())){
            throw new RestApiException(CustomErrorCode.UNAUTHORIZED_RECRUIT_REQUEST);
        }
        return comment;
    }

    public Comment getCommentById(Long id) {
        return commentRepository.findById(id).orElseThrow(() -> new RestApiException(CommonErrorCode.RESOURCE_NOT_FOUND));
    }

    private Recomment getRecommentById(Long id){
        return recommentRepository.findById(id)
                .orElseThrow(()->new RestApiException(CommonErrorCode.RESOURCE_NOT_FOUND));
    }
}
