package com.jino.realbread.global.oauth.entity;

import com.jino.realbread.domain.user.entity.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

public class CustomOAuth2User implements OAuth2User {

    private final UserEntity userEntity;
    private final Map<String, Object> attributes;

    public CustomOAuth2User(UserEntity userEntity, Map<String, Object> attributes) {
        this.userEntity = userEntity;
        this.attributes = attributes;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.createAuthorityList(userEntity.getRole().name());
    }

    @Override
    public String getName() {
        return String.valueOf(userEntity.getId());
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }
}
