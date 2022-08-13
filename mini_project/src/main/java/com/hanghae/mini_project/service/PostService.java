package com.hanghae.mini_project.service;

import com.hanghae.mini_project.dto.requestDto.postReqDto.PostCreateDto;
import com.hanghae.mini_project.dto.requestDto.postReqDto.PostRequestDto;
import com.hanghae.mini_project.dto.responseDto.ResponseDto;
import com.hanghae.mini_project.entity.Post;
import com.hanghae.mini_project.entity.TechStack;
import com.hanghae.mini_project.entity.User;
import com.hanghae.mini_project.repository.PostRepository;
import com.hanghae.mini_project.repository.TechStackRespository;
import com.hanghae.mini_project.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final TechStackRespository techStackRespository;

    @Transactional
    public ResponseDto<?> createPost(PostRequestDto postRequestDto, UserDetailsImpl userDetails) {

        // 기술스택 리스트를 PostRequestDto에서 추출
        List<String> stackList = postRequestDto.getTechStackList().getStackList();

        // 글 내용 및 작성자를 넣어서 공고글 객체 생성 후 DB저장
        Post post = new Post(new PostCreateDto(postRequestDto.getDescription(), userDetails.getUser()));
        postRepository.save(post);

        // 이 공고글에서 체크된 기술스택 DB에 저장
        for(String stack : stackList) {
            techStackRespository.save(new TechStack(stack,post));
        }
        return ResponseDto.success("공고글 작성이 완료되었습니다.", post);

    }

    @Transactional
    public ResponseEntity<?> update(Long id, PostRequestDto postRequestDto, UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        if(!postRepository.findById(id).isPresent()){
            return new ResponseEntity<>(ResponseDto.fail("404_NOT_FOUND",
                    "찾으시는 게시글이 없습니다."), HttpStatus.NOT_FOUND);
        }
        Post post = postRepository.findById(id).get();

        if(post.getUser().equals(user)) {
            post.update(postRequestDto);
            return new ResponseEntity<>(ResponseDto.success("공고글 수정이 완료되었습니다.",post), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(ResponseDto.fail("403_FORBIDDEN" ,
                    "이 공고글의 작성자가 아니므로 수정 권한이 없습니다."), HttpStatus.FORBIDDEN);
        }
    }

    @Transactional
    public ResponseEntity<?> deletePost(Long id, UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        if(!postRepository.findById(id).isPresent()){
            return new ResponseEntity<>(ResponseDto.fail("404_NOT_FOUND",
                    "찾으시는 게시글이 없습니다."), HttpStatus.NOT_FOUND);
        }
        Post post = postRepository.findById(id).get();

        if(post.getUser().equals(user)) {
            postRepository.delete(post);
            return new ResponseEntity<>(ResponseDto.success("공고글 삭제가 완료되었습니다.",null), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(ResponseDto.fail("403_FORBIDDEN" ,
                    "이 공고글의 작성자가 아니므로 삭제 권한이 없습니다."), HttpStatus.FORBIDDEN);
        }
    }
}
