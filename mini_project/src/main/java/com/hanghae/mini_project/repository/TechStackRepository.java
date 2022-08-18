package com.hanghae.mini_project.repository;

import com.hanghae.mini_project.entity.Post;
import com.hanghae.mini_project.entity.TechStack;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TechStackRepository extends JpaRepository<TechStack, Long> {
    Optional<TechStack> findTechStackByStackNameAndPost(String stackName, Post post);

    List<TechStack> findAllByPost(Post post);
}
