package com.hanghae.mini_project.repository;


import com.hanghae.mini_project.entity.Comment;
import com.hanghae.mini_project.entity.Recomment;
import com.hanghae.mini_project.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RecommentRepository extends JpaRepository<Recomment,Long> {

    Optional<Recomment> findByCommentAndUser(Comment comment, User user);
    Optional<Recomment> findByComment(Comment comment);

}
