package com.jino.realbread.domain.user.repository;

import com.jino.realbread.domain.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findByOauthId(String oauthId);

    boolean existsById(Integer userId);

    UserEntity finaByUserEmail(String email);
}
