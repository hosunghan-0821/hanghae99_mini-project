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

        public static final String RECRUITER_STR="구인자";
        public static final String JOB_SEEKER_STR="구직자";
        public static final String RECRUITER="ROLE_RECRUITER";
        public static final String JOB_SEEKER ="ROLE_JOB_SEEKER";
    }
}
