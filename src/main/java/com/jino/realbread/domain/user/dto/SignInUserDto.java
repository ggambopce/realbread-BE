package com.jino.realbread.domain.user.dto;

import com.jino.realbread.domain.user.entity.UserEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SignInUserDto {
    private Long userId;
    private String email;
    private String nickname;
    private String profileImage;

    public SignInUserDto(UserEntity entity) {
        this.userId = entity.getUserId();
        this.email = entity.getEmail();
        this.nickname = entity.getNickname();
        this.profileImage = entity.getProfileImage();
    }
}
