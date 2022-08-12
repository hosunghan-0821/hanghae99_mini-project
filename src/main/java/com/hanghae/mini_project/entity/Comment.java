package com.hanghae.mini_project.entity;


import lombok.Getter;

import javax.persistence.*;
import java.util.List;

@Getter
@Entity
public class Comment {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private String content;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;


    //연관관계
    //recommentList
    @OneToMany(mappedBy = "comment", fetch = FetchType.LAZY ,cascade =CascadeType.REMOVE)
    private List<Recomment> recommentList;

}
