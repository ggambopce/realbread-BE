package com.jino.realbread.domain.user.repository;

import com.jino.realbread.domain.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long> {

    Optional<UserEntity> findByOauthIdAndProvider(String oauthId, String kakao);
}
