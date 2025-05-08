package com.jino.realbread.domain.user.entity;

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
    private Long id;

    private String oauthId;
    private String provider;

    @Column(unique = true)
    private String email;
    private String nickname;
    private String profileImage;

    @Enumerated(EnumType.STRING)
    private Role role;

    public UserEntity (String oauthId, String provider) {
        this.oauthId = oauthId;
        this.provider = provider;
        this.role = Role.ROLE_USER; // 기본 권한 설정
        this.email = null;
        this.nickname = null;
        this.profileImage = null;

    }


}
