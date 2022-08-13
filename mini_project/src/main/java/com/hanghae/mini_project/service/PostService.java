package com.hanghae.mini_project.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hanghae.mini_project.dto.ResponseDto;
import com.hanghae.mini_project.dto.requestDto.postReqDto.PostCreateDto;
import com.hanghae.mini_project.dto.requestDto.postReqDto.PostRequestDto;
import com.hanghae.mini_project.dto.requestDto.postReqDto.TechStackDto;
import com.hanghae.mini_project.entity.Post;
import com.hanghae.mini_project.entity.TechStack;
import com.hanghae.mini_project.entity.User;
import com.hanghae.mini_project.repository.PostRepository;
import com.hanghae.mini_project.repository.TechStackRespository;
import com.hanghae.mini_project.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final TechStackRespository techStackRespository;

    @Transactional
    public ResponseDto<?> createPost(PostRequestDto postRequestDto, UserDetailsImpl userDetails) {

        // 기술스택 리스트를 PostRequestDto에서 추출
        List<String> stackList = postRequestDto.getTechStackList().getStackList();

        User user = userDetails.getUser();

        // 글 내용 및 작성자를 넣어서 공고글 객체 생성 후 DB저장
        Post post = new Post(new PostCreateDto(postRequestDto.getDescription(), user));
        postRepository.save(post);

        // 이 공고글에서 체크된 기술스택 DB에 저장
        for(String stack : stackList) {
            techStackRespository.save(new TechStack(stack,post));
        }
        return ResponseDto.success("공고글 작성이 완료되었습니다.");

    }

    public ResponseDto<?> update(Long id, PostRequestDto postRequestDto, UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalStateException("찾으시는 게시글이 없습니다.")
        );

        post.update(postRequestDto);

        return ResponseDto.success(post);

    }
}
