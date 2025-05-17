package com.jino.realbread.domain.user.entity;

import com.jino.realbread.global.oauth.dto.OAuthUserInfoDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "user")
@Table(name = "user")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long userId;

    private String oauthId;

    @Column(unique = true)
    private String email;
    private String nickname;
    private String profileImage;

    @Enumerated(EnumType.STRING)
    private Role role;

    public UserEntity(String oauthId, String email, String nickname, String profileImage, Role role) {
        this.oauthId = oauthId;
        this.email = email;
        this.nickname = nickname;
        this.profileImage = profileImage;
        this.role = Role.ROLE_USER; // 기본 권한 부여
    }

    public static UserEntity from(OAuthUserInfoDto dto) {
        return new UserEntity(
                dto.getOauthId(),
                dto.getEmail(),
                dto.getNickname(),
                dto.getProfileImage(),
                Role.ROLE_USER);
    }

}
