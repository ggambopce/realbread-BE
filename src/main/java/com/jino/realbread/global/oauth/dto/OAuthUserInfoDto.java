package com.jino.realbread.global.oauth.dto;

import com.jino.realbread.domain.user.entity.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OAuthUserInfoDto {
    private String oauthId;
    private String email;
    private String nickname;
    private String profileImage;
}
