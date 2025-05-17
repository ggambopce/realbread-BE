package com.jino.realbread.domain.user.service;

import org.springframework.http.ResponseEntity;

import com.jino.realbread.global.security.auth.PrincipalDetails;

public interface UserService {
    ResponseEntity<GetSignInUserResponseDto> getSignInUser(PrincipalDetails principalDetails);
}
