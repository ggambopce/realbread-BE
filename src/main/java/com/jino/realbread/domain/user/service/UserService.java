package com.jino.realbread.domain.user.service;

import org.springframework.http.ResponseEntity;

import com.jino.realbread.domain.user.dto.response.GetSignInUserResponseDto;
import com.jino.realbread.global.security.auth.PrincipalDetails;

public interface UserService {
    ResponseEntity<? super GetSignInUserResponseDto> getSignInUser(PrincipalDetails principalDetails);
}
