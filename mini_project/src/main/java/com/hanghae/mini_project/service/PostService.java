package com.hanghae.mini_project.service;

import com.hanghae.mini_project.dto.requestDto.postReqDto.PostCreateDto;
import com.hanghae.mini_project.dto.requestDto.postReqDto.PostRequestDto;
import com.hanghae.mini_project.dto.responseDto.ResponseDto;
import com.hanghae.mini_project.entity.Post;
import com.hanghae.mini_project.entity.TechStack;
import com.hanghae.mini_project.entity.User;
import com.hanghae.mini_project.repository.PostRepository;
import com.hanghae.mini_project.repository.TechStackRepository;
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
    private final TechStackRepository techStackRepository;

    @Transactional
    public ResponseDto<?> createPost(PostRequestDto postRequestDto, UserDetailsImpl userDetails) {

        // 기술스택 리스트를 PostRequestDto에서 추출
        List<String> stackList = postRequestDto.getTechStackList().getStackList();
        Post post = new Post(new PostCreateDto(postRequestDto.getDescription(), userDetails.getUser()));

        // 이 공고글에서 체크된 기술스택 DB에 저장
        for(String stack : stackList) {
            TechStack techStack = new TechStack(stack, post);
            techStackRepository.save(techStack);
            post.addTechStack(techStack);
        }
        return ResponseDto.success("공고글 작성이 완료되었습니다.", postRepository.save(post));

    }

    @Transactional
    public ResponseEntity<?> update(Long id, PostRequestDto postRequestDto, UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        if(!postRepository.findById(id).isPresent()){
            return new ResponseEntity<>(ResponseDto.fail("404_NOT_FOUND",
                    "찾으시는 게시글이 없습니다."), HttpStatus.NOT_FOUND);
        }
        Post post = postRepository.findById(id).get();

        if(post.getUser().getUsername().equals(user.getUsername())) {
            List<String> modifiedStackList = postRequestDto.getTechStackList().getStackList();
            List<TechStack> stacksByPost = techStackRepository.findAllByPost(post);

            /*
                수정 요청 스택들과 이 게시글에 이전 등록된 스택들을 비교하여,
                이미 등록되어 있던 스택이 수정리스트 안에 없을 경우 TechStack 테이블에서 삭제
             */
            for(TechStack techStack : stacksByPost) {
                if(!modifiedStackList.contains(techStack.getStackName())) {
                    techStackRepository.delete(techStack);
                    post.removeTechStack(techStack);
                }
            }

             /*
               수정 요청 스택들 중 새롭게 추가된 스택들을 TechStack 테이블에 저장
             */
            for(String stack : modifiedStackList) {
                if(!techStackRepository.findTechStackByStackNameAndPost(stack,post).isPresent()) {
                    TechStack techStack = new TechStack(stack, post);
                    techStackRepository.save(techStack);
                    post.addTechStack(techStack);
                }
            }
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

        if(post.getUser().getUsername().equals(user.getUsername())) {
            postRepository.delete(post);
            return new ResponseEntity<>(ResponseDto.success("공고글 삭제가 완료되었습니다.",null), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(ResponseDto.fail("403_FORBIDDEN" ,
                    "이 공고글의 작성자가 아니므로 삭제 권한이 없습니다."), HttpStatus.FORBIDDEN);
        }
    }
}
