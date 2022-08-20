package com.hanghae.mini_project.service;

import com.hanghae.mini_project.dto.requestDto.CommentRequestDto;
import com.hanghae.mini_project.dto.responseDto.CommentResponseDto;
import com.hanghae.mini_project.entity.Comment;
import com.hanghae.mini_project.entity.Post;
import com.hanghae.mini_project.exception.ErrorCode.CommonErrorCode;
import com.hanghae.mini_project.exception.ErrorCode.CustomErrorCode;
import com.hanghae.mini_project.exception.Exception.RestApiException;
import com.hanghae.mini_project.repository.CommentRepository;
import com.hanghae.mini_project.repository.PostRepository;
import com.hanghae.mini_project.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostService postService;


    @Transactional
    public CommentResponseDto saveComment(Long postId, CommentRequestDto commentRequestDto, UserDetailsImpl userDetails) {

        Post post = postService.getPostById(postId);

        Comment comment = Comment.builder()
                .user(userDetails.getUser())
                .post(post)
                .content(commentRequestDto.getContent())
                .build();

        commentRepository.save(comment);

        return CommentResponseDto.of(comment);
    }
    


    @Transactional
    public CommentResponseDto updateComment(Long postId, CommentRequestDto commentRequestDto, UserDetailsImpl userDetails) {
        Comment comment = checkValidateComment(userDetails, postId, commentRequestDto.getCommentId());
        comment.update(commentRequestDto.getContent());

        return CommentResponseDto.of(comment);
    }


    @Transactional
    public CommentResponseDto deleteComment(Long postId,  CommentRequestDto commentRequestDto, UserDetailsImpl userDetails) {
        Comment comment = checkValidateComment(userDetails, postId, commentRequestDto.getCommentId());
        commentRepository.delete(comment);
        
        return CommentResponseDto.of(comment);
    }

    
    
    //문의사항을 작성한 작성자가 맞는지 확인하는 메서드
    private Comment checkValidateComment(UserDetailsImpl userDetails, Long postId, Long commentId) {
        postService.getPostById(postId);

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RestApiException(CommonErrorCode.RESOURCE_NOT_FOUND));

        //문의사항 수정은 문의사항 작성자만 가능할 수 있게 해야하기 때문에 Exception 처리해야함
        if(!comment.getUser().getId().equals(userDetails.getUser().getId())) {
            throw new RestApiException(CustomErrorCode.UNAUTHORIZED_SEEKER_REQUEST);
        }

        return comment;
    }
}
