package com.jino.realbread.domain.user.service.implement;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.jino.realbread.domain.user.dto.response.GetSignInUserResponseDto;
import com.jino.realbread.domain.user.entity.UserEntity;
import com.jino.realbread.domain.user.repository.UserRepository;
import com.jino.realbread.domain.user.service.UserService;
import com.jino.realbread.global.dto.response.ResponseDto;
import com.jino.realbread.global.security.auth.PrincipalDetails;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImplement implements UserService {

    private final UserRepository userRepository;

    @Override
    public ResponseEntity<? super GetSignInUserResponseDto> getSignInUser(PrincipalDetails principalDetails) {

        UserEntity userEntity = null;
        try {
            Stirng email = principalDetails.getUserEntity().getEmail();

            userEntity = userRepository.finaByEmail(email);
            if (userEntity == null)
                return GetSignInUserResponseDto.noExistUser();

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return GetSignInUserResponseDto.success(userEntity);
    }

}
