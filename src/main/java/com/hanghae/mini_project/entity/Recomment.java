package com.hanghae.mini_project.entity;


import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
public class Recomment {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment comment;

}
