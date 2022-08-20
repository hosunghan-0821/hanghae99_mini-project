package com.hanghae.mini_project.dto.responseDto;

import com.hanghae.mini_project.entity.User;
import com.hanghae.mini_project.entity.UserRoleEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {

    private String username;
    private String profileImageUrl;
    private String companyName;
    private String websiteUrl;
    private UserRoleEnum role;

    public UserDto(User user) {
        this.username = user.getUsername();
        this.profileImageUrl = user.getProfileImageUrl();
        this.role = user.getRole();

        if(user.getRole().equals(UserRoleEnum.RECRUITER)) {
            this.companyName = user.getCompanyName();
            this.websiteUrl = user.getWebsiteUrl();
        }
    }
}
