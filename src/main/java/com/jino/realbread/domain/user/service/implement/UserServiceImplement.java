package com.jino.realbread.domain.user.service.implement;

import org.springframework.stereotype.Service;

import com.jino.realbread.domain.user.service.UserService;
import com.jino.realbread.global.security.auth.PrincipalDetails;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImplement implements UserService {
    @Override
    public ResponseEntity<? super GetSignInUserResponseDto> getSignInUser(PrincipalDetails principalDetails) {

    }

}
