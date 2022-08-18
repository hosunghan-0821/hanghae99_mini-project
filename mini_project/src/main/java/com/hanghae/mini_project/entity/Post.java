package com.hanghae.mini_project.entity;


import com.hanghae.mini_project.dto.requestDto.postReqDto.PostCreateDto;
import com.hanghae.mini_project.dto.requestDto.postReqDto.PostRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Setter
@NoArgsConstructor
@AllArgsConstructor
    public class Post extends Timestamped{

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column
    private String description;

    @Column
    private String jobTitle;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    //연관관계 설정해놓기.

    // stackList
    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY ,cascade =CascadeType.REMOVE)
    private List<TechStack> techStackList = new ArrayList<>();

    // commentList
    // CascadeType.Remove & All 차이 공부
    // mappedBy 연관관계의 주인이 아니다 (난 FK가 아니다 DB에 칼럼을 만들지 말아라)
    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY ,cascade =CascadeType.REMOVE) //cascade all ,orphan
    private List<Comment> commentList = new ArrayList<>();



    public Post(PostCreateDto postCreateDto) {
        this.jobTitle = postCreateDto.getJobTitle();
        this.description = postCreateDto.getDescription();
        this.user = postCreateDto.getUser();
    }

    public void update(PostRequestDto postRequestDto) {
        this.jobTitle = postRequestDto.getJobTitle();
        this.description = postRequestDto.getDescription();
    }

    public void addTechStack(TechStack techStack) {
        this.techStackList.add(techStack);
    }

    public void removeTechStack(TechStack techStack) {
        this.techStackList.remove(techStack);
    }
}
