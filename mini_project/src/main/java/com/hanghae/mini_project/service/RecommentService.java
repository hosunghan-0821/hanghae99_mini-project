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
    public RecommentResponseDto saveRecomment(UserDetailsImpl userDetails,
                                              RecommentRequestDto requestDto,Long commentId){

        //comment가 유효한지 확인 및 작성자 확인 후, recomment 작성
        Comment comment = checkValidatecomment(userDetails,commentId);

        //DB에 저장할 recomment 객체 생성
        Recomment recomment = Recomment.builder()
                    .user(userDetails.getUser())
                    .comment(comment)
                    .content(requestDto.getContent())
                    .build();


        //DB에 문의사항에 대한 답변 작성
        recommentRepository.save(recomment);

        //ResponseDto 생성 후, return
        return makeResponseDto(recomment);
    }


    @Transactional
    public RecommentResponseDto updateRecomment(UserDetailsImpl userDetails,
                                                RecommentRequestDto requestDto, Long commentId){

        //comment가 유효한지 확인 및 작성자 확인 후, recomment 작성
        checkValidatecomment(userDetails,commentId);
        Recomment recomment=checkValidateRecomment(userDetails,requestDto.getRecommentId());

        recomment.update(requestDto.getContent());
        recommentRepository.save(recomment);
        return makeResponseDto(recomment);
    }


    @Transactional
    public RecommentResponseDto deleteRecomment(UserDetailsImpl userDetails,
                                                RecommentRequestDto requestDto, Long commentId){
        checkValidatecomment(userDetails,commentId);
        Recomment recomment=checkValidateRecomment(userDetails,requestDto.getRecommentId());
        recommentRepository.delete(recomment);

        return makeResponseDto(recomment);
    }

    private Comment checkValidatecomment(UserDetailsImpl userDetails, Long commentId){

        //commentID로부터 comment 객체 받아오기
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(()->new RestApiException(CommonErrorCode.RESOURCE_NOT_FOUND));

        //문의사항 답변은 게시글 작성자만 할 수 있게만 해야하기 때문에 Exception으로 컽해야함.
        if(!(comment.getPost().getUser().getId() == userDetails.getUser().getId())){
            //throw new IllegalArgumentException("작성자만 문의사항에 답변할 수 있습니다.");
            throw new RestApiException(CustomErrorCode.UNAUTHORIZED_RECRUIT_REQUEST);
        }
        return comment;
    }

    private Recomment checkValidateRecomment(UserDetailsImpl userDetails,Long recommentId){
        //recoomentId로 부터 recomment 객체 받아오기
        Recomment recomment = recommentRepository.findById(recommentId)
                .orElseThrow(()->new RestApiException(CommonErrorCode.RESOURCE_NOT_FOUND));
        return recomment;
    }

    private RecommentResponseDto makeResponseDto(Recomment recomment){

        return RecommentResponseDto.builder()
                .username(recomment.getUser().getUsername())
                .createdAt(recomment.getCreatedAt())
                .modifiedAt(recomment.getModifiedAt())
                .id(recomment.getId())
                .content(recomment.getContent())
                .build();

    }
}
