package com.hanghae.mini_project.repository;

import com.hanghae.mini_project.entity.TechStack;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TechStackRespository extends JpaRepository<TechStack, Long> {
}
