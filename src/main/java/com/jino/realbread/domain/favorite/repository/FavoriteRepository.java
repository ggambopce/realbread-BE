package com.jino.realbread.domain.favorite.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jino.realbread.domain.favorite.entity.FavoriteEntity;
import com.jino.realbread.domain.favorite.entity.primaryKey.FavoritePK;
import com.jino.realbread.domain.favorite.repository.resultSet.GetFavoriteListResultSet;

@Repository
public interface FavoriteRepository extends JpaRepository<FavoriteEntity, FavoritePK> {

    FavoriteEntity findByBakeryNumberAndUserId(Integer bakeryNumber, Long userId);

    @Query(value = "SELECT " +
            "U.email AS email, " +
            "U.nickname AS nickname, " +
            "U.profile_image AS profileImage " +
            "FROM favorite AS F " +
            "INNER JOIN user AS U " +
            "ON F.user_id = U.id " +
            "WHERE F.bakery_number = ?1", nativeQuery = true)
    List<GetFavoriteListResultSet> getFavoriteList(Integer bakeryNumber);

}
