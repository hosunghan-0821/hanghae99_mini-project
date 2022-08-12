package com.hanghae.mini_project.entity;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class TechStack {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private String stackName;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;
}
