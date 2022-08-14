package com.hanghae.mini_project.service;

import com.hanghae.mini_project.dto.responseDto.PostResponse;
import com.hanghae.mini_project.entity.Post;
import com.hanghae.mini_project.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
}

    public PostResponse getPost(Long id) {
        // 게시글을 조회하고 게시글 안에 있는 내용을 보내줘야함
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("없는 게시물 아이디 입니다.")); // 아이디로 게시글을 조회했는데, 있을수도있고 없을수도있음
        // 조회해서 board가 나왔는데,
        // board -> boardResponse로 바꿔서 리턴 하고싶음

        // 1. board에 있는 값들을 꺼낸다. 몽땅
        String description = post.getDescription();

        // 2. BoardResponse 객체를 생성한다. (생성할 때 board에서 꺼낸 값을 넣어줌)
        PostResponse postResponse = new PostResponse(post);
        // 3. 생성한 BoardResponse 리턴한다.
        return postResponse;
    }

    public List<PostResponse> getposts() {
        List<Post> posts = postRepository.findAll();
        List<PostResponse> postResponses = new ArrayList<>();
        for (int i  = 0; i < posts.size(); i++) {
            Post post = posts.get(i);
            PostResponse postResponse = new PostResponse(post);
            postResponse.add(postResponse);
        }
        return postResponses;
    }

}
