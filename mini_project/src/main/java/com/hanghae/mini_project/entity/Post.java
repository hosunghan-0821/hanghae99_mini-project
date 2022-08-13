package com.hanghae.mini_project.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Entity
@Setter
@NoArgsConstructor
public class Post {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column
    private String description;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    //연관관계 설정해놓기.

    // stackList
    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY ,cascade =CascadeType.REMOVE)
    private List<TechStack> techStackList;

    // commentList
    //CascadeType.Remove & All 차이 공부
    // mappedBy 연관관계의 주인이 아니다 (난 FK가 아니다 DB에 칼럼을 만들지 말아라)
    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY ,cascade =CascadeType.REMOVE)
    private List<Comment> commentList;


}
