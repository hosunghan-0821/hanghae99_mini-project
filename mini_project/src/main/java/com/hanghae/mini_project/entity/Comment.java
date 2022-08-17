package com.hanghae.mini_project.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Comment extends Timestamped{

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
    private List<Recomment> recommentList = new ArrayList<>();

    public void update(String content) {
        this.content = content;
    }
}
