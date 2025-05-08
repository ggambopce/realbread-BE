package com.jino.realbread.global.oauth.entity;

import com.jino.realbread.domain.user.entity.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

public class CustomOAuth2User implements OAuth2User {

    private final UserEntity user;

    public CustomOAuth2User(UserEntity user) {
        this.user = user;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return Map.of("id", user.getId());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.createAuthorityList(user.getRole().name());
    }

    @Override
    public String getName() {
        return user.getId().toString();
    }

    public UserEntity getUser() {
        return user;
    }
}
