package com.jino.realbread.global.oauth.service.implement;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jino.realbread.domain.user.entity.Role;
import com.jino.realbread.domain.user.entity.UserEntity;
import com.jino.realbread.domain.user.repository.UserRepository;
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

    private UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest request) throws OAuth2AuthenticationException {
         OAuth2User oAuth2User = super.loadUser(request);

        Map<String, Object> attributes = oAuth2User.getAttributes();

        // kakao에서 유저 정보 꺼내기
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");

        String oauthId = String.valueOf(attributes.get("id"));
        String email = (String) kakaoAccount.get("email");
        String nickname = (String) profile.get("nickname");
        String profileImage = (String) profile.get("profile_image_url");

        // DB에 해당 oauthId가 있는지 확인
        UserEntity userEntity = userRepository.findByOauthIdAndProvider(oauthId, "kakao")
                .orElseGet(() -> {
                    // 없으면 저장
                    UserEntity newUser = new UserEntity(null, oauthId, "kakao", email, nickname, profileImage, Role.ROLE_USER);
                    return userRepository.save(newUser);
                });

         return new CustomOAuth2User(userEntity, attributes);
    }


}
