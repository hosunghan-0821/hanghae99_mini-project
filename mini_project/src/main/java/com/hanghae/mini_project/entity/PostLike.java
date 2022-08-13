package com.hanghae.mini_project.entity;


import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
public class PostLike {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

}
