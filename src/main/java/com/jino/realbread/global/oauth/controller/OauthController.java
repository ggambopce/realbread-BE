package com.jino.realbread.global.oauth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jino.realbread.domain.user.dto.response.GetSignInUserResponseDto;
import com.jino.realbread.domain.user.service.UserService;
import com.jino.realbread.global.security.auth.PrincipalDetails;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class OauthController {

    private final UserService userService;

    @GetMapping("/user")
    public ResponseEntity<? super GetSignInUserResponseDto> getSignInUser(
            @AuthenticationPrincipal PrincipalDetails principalDetails) {
        ResponseEntity<? super GetSignInUserResponseDto> response = userService.getSignInUser(principalDetails);
        return response;
    }
}
