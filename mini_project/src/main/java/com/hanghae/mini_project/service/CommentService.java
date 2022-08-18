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
    private final PostRepository postRepository;


    @Transactional
    public CommentResponseDto saveComment(Long postId, CommentRequestDto commentRequestDto,
                                          UserDetailsImpl userDetails) {

        //특정 공고글에 댓글이 추가되는 것이기 때문에 공고글이 있는지 먼저 확인
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RestApiException(CommonErrorCode.RESOURCE_NOT_FOUND));

        Comment comment = Comment.builder()
                .user(userDetails.getUser())
                .post(post)
                .content(commentRequestDto.getContent())
                .build();

        commentRepository.save(comment);

        return makeResponseDto(comment);
    }
    


    @Transactional
    public CommentResponseDto updateComment(Long postId, CommentRequestDto commentRequestDto,
                              UserDetailsImpl userDetails) {
        

        Comment comment = checkValidateComment(userDetails, postId, commentRequestDto.getCommentId());

        comment.update(commentRequestDto.getContent());
        commentRepository.save(comment);
        return makeResponseDto(comment);
    }


    @Transactional
    public CommentResponseDto deleteComment(Long postId,  CommentRequestDto commentRequestDto,
                              UserDetailsImpl userDetails) {

        Comment comment = checkValidateComment(userDetails, postId, commentRequestDto.getCommentId());
        commentRepository.delete(comment);
        
        return makeResponseDto(comment);
    }

    
    
    //문의사항을 작성한 작성자가 맞는지 확인하는 메서드
    private Comment checkValidateComment(UserDetailsImpl userDetails, Long postId, Long commentId) {

        //공고글이 있는지 확인
        postRepository.findById(postId)
                .orElseThrow(() -> new RestApiException(CommonErrorCode.RESOURCE_NOT_FOUND));

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RestApiException(CommonErrorCode.RESOURCE_NOT_FOUND));

        //문의사항 수정은 문의사항 작성자만 가능할 수 있게 해야하기 때문에 Exception 처리해야함
        if(!comment.getUser().getId().equals(userDetails.getUser().getId())) {
            throw new RestApiException(CustomErrorCode.UNAUTHORIZED_SEEKER_REQUEST);
        }

        return comment;
    }

    private CommentResponseDto makeResponseDto(Comment comment) {
        return CommentResponseDto.builder()
                .username(comment.getUser().getUsername())
                .createdAt(comment.getCreatedAt())
                .modifiedAt(comment.getModifiedAt())
                .profileImageUrl(comment.getUser().getProfileImageUrl())
                .id(comment.getId())
                .content(comment.getContent())
                .build();
    }
    
}
