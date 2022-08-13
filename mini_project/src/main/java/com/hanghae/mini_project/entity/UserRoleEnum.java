package com.hanghae.mini_project.entity;

public enum UserRoleEnum {

    RECRUITER(Authority.RECRUITER),
    JOB_SEEKER(Authority.JOB_SEEKER);

    private final String authority;

    UserRoleEnum(String authority){
        this.authority=authority;
    }
    public String getAuthority() {
        return this.authority;
    }
    public static class Authority{
        public static final String RECRUITER="구인자";
        public static final String JOB_SEEKER ="구직자";
    }
}
