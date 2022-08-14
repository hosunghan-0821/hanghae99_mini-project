package com.hanghae.mini_project.dto.requestDto;

public class PostRequest {

    private String description;

    public PostRequest(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
