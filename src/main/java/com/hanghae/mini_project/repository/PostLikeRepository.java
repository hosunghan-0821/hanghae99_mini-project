package com.hanghae.mini_project.repository;

import com.hanghae.mini_project.entity.Post;
import com.hanghae.mini_project.entity.PostLike;
import com.hanghae.mini_project.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike,Long> {
    Optional<PostLike> findInterestPostByUserAndPost(User user, Post post);
    void deleteByUserAndPost(User user, Post post);
}
