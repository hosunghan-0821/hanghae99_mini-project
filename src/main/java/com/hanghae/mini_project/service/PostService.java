package com.hanghae.mini_project.service;

import com.hanghae.mini_project.dto.requestDto.postReqDto.PostCreateDto;
import com.hanghae.mini_project.dto.requestDto.postReqDto.PostLikeDto;
import com.hanghae.mini_project.dto.requestDto.postReqDto.PostRequestDto;
import com.hanghae.mini_project.dto.responseDto.PostDto;
import com.hanghae.mini_project.dto.responseDto.ResponseDto;
import com.hanghae.mini_project.entity.Post;
import com.hanghae.mini_project.entity.PostLike;
import com.hanghae.mini_project.entity.TechStack;
import com.hanghae.mini_project.entity.User;
import com.hanghae.mini_project.repository.PostLikeRepository;
import com.hanghae.mini_project.repository.PostRepository;
import com.hanghae.mini_project.repository.TechStackRepository;
import com.hanghae.mini_project.repository.UserRepository;
import com.hanghae.mini_project.security.UserDetailsImpl;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final TechStackRepository techStackRepository;

    private final UserRepository userRepository;

    private final PostLikeRepository postLikeRepository;


    @Transactional(readOnly = true)
    public ResponseEntity<?> getAllPosts() {
        List<Post> allPosts = postRepository.findByOrderByCreatedAtDesc();

        List<PostDto> postDtoList = new ArrayList<>();
        for (Post post : allPosts) {
            PostDto postDto = new PostDto(post);
            postDto.updateComments(post.getCommentList());
            postDto.updateStackList(post.getTechStackList());
            postDtoList.add(postDto);
        }

        return new ResponseEntity<>(ResponseDto.success("공고글 전체목록", postDtoList), HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> getPostDetail(Long id) {
        Post foundPost = getPostById(id);

        return new ResponseEntity<>(ResponseDto.success("게시글 상세조회",
                new PostDto(foundPost)), HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?> createPost(PostRequestDto postRequestDto, UserDetailsImpl userDetails) {
        List<String> stackList = postRequestDto.getTechStackList().getStackList();
        Post post = new Post(new PostCreateDto(postRequestDto.getJobTitle(), postRequestDto.getDescription(), userDetails.getUser()));

        for(String stack : stackList) {
            TechStack techStack = new TechStack(stack, post);
            techStackRepository.save(techStack);
            post.addTechStack(techStack);
        }

        Post savedPost = postRepository.save(post);
        return new ResponseEntity<>(ResponseDto.success("공고글 작성이 완료되었습니다.",
                new PostDto(savedPost)), HttpStatus.OK);

    }

    @Transactional
    public ResponseEntity<?> update(Long id, PostRequestDto postRequestDto, UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        Post post = getPostById(id);

        if (!post.getUser().getUsername().equals(user.getUsername())) {
            throw new IllegalArgumentException("이 공고글의 작성자가 아니므로 수정 권한이 없습니다.");
        }

        List<String> modifiedStackList = postRequestDto.getTechStackList().getStackList();
        List<TechStack> stacksByPost = techStackRepository.findAllByPost(post);

        for(TechStack techStack : stacksByPost) {
            if(!modifiedStackList.contains(techStack.getStackName())) {
                techStackRepository.delete(techStack);
                post.removeTechStack(techStack);
            }
        }

        for(String stack : modifiedStackList) {
            if(!techStackRepository.findTechStackByStackNameAndPost(stack,post).isPresent()) {
                TechStack techStack = new TechStack(stack, post);
                techStackRepository.save(techStack);
                post.addTechStack(techStack);
            }
        }

        post.update(postRequestDto);
        return new ResponseEntity<>(ResponseDto.success("공고글 수정이 완료되었습니다.",new PostDto(post)), HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?> deletePost(Long id, UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        Post post = getPostById(id);

        if (!post.getUser().getUsername().equals(user.getUsername())) {
            throw new IllegalArgumentException("이 공고글의 작성자가 아니므로 삭제 권한이 없습니다.");
        }

        postRepository.delete(post);
        return new ResponseEntity<>(ResponseDto.success("공고글 삭제가 완료되었습니다.",null), HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?> addInterestPosts(Long id, UserDetailsImpl userDetails) {
        Post post = getPostById(id);
        User foundUser = userRepository.findByUsername(userDetails.getUser().getUsername()).get();

        PostLikeDto postLikeDto = new PostLikeDto(foundUser,post);
        PostLike postLike = new PostLike(postLikeDto);

        if(postLikeRepository.findInterestPostByUserAndPost(foundUser, post).isPresent()) {
            postLikeRepository.deleteByUserAndPost(foundUser, post);
            foundUser.cancelInterest(postLike);
            return new ResponseEntity<>(ResponseDto.success("관심목록 삭제완료!", new PostDto(post)), HttpStatus.OK);
        }

        // 관심목록 추가
        foundUser.addInterest(postLike);
        postLikeRepository.save(postLike);

        return new ResponseEntity<>(ResponseDto.success("관심목록 등록완료!", new PostDto(post)), HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public Post getPostById(Long id) {
        return postRepository.findById(id).orElseThrow(() -> new IllegalStateException("찾으시는 게시글이 없습니다."));
    }
}
