package com.jino.realbread.global.oauth.service.implement;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jino.realbread.domain.user.entity.Role;
import com.jino.realbread.domain.user.entity.UserEntity;
import com.jino.realbread.domain.user.repository.UserRepository;
import com.jino.realbread.global.oauth.dto.OAuthUserInfoDto;
import com.jino.realbread.global.oauth.entity.CustomOAuth2User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthServiceImplement extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest request) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(request);

        Map<String, Object> attributes = oAuth2User.getAttributes();

        // kakao에서 유저 정보 꺼내기
        String oauthId = String.valueOf(attributes.get("id"));
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");

        String email = (String) kakaoAccount.get("email");
        String nickname = (String) profile.get("nickname");
        String profileImage = (String) profile.get("profile_image_url");

        // DTO 구성
        OAuthUserInfoDto dto = new OAuthUserInfoDto();
        dto.setOauthId(oauthId);
        dto.setEmail(email);
        dto.setNickname(nickname);
        dto.setProfileImage(profileImage);

        // 저장 or 조회
        UserEntity userEntity = userRepository.findByOauthId(oauthId)
                .orElseGet(() -> userRepository.save(UserEntity.from(dto)));

        // 반환할 사용자 정의 OAuth2User 생성
        return new CustomOAuth2User(userEntity);
    }

}
