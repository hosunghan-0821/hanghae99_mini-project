package com.hanghae.mini_project.repository;

import com.hanghae.mini_project.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Long> {
}
