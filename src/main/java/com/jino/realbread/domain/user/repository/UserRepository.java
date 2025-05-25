package com.jino.realbread.domain.user.repository;

import com.jino.realbread.domain.user.entity.UserEntity;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByOauthId(String oauthId);

    boolean existsByUserId(Long userId);

    UserEntity findByEmail(String email);
}
