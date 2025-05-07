package com.jino.realbread.domain.user.entity;

import jakarta.persistence.*;

@Entity
public class User {

    @Id
    @GeneratedValue
    private Long id;

    private String oauthId;
    private String provider;

    private String email;
    private String nickname;
    private String profileImage;

    @Enumerated(EnumType.STRING)
    private Role role;
}
