package com.jino.realbread.domain.bakery.repository;

import com.jino.realbread.domain.bakery.repository.resultSet.BakeryMarkerListItem;
import com.jino.realbread.domain.bakery.entity.Bakery;
import com.jino.realbread.domain.bakery.repository.resultSet.GetBakeryResultSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface BakeryRepository extends JpaRepository<Bakery, Integer> {

    boolean existsByAddress(String address);

    @Query(value = "SELECT " +
            "B.id AS bakeryNumber, " +
            "B.title AS title, " +
            "B.road_address AS roadAddress, " +
            "B.favorite_count AS favoriteCount, " +
            "B.comment_count AS commentCount, " +
            "M.id AS menuNumber, " +
            "M.menu_name AS menuName, " +
            "M.price AS price, " +
            "M.description AS description, " +
            "M.image_url AS imageUrl " +
            "FROM bakery AS B " +
            "INNER JOIN menu AS M " +
            "ON B.id = M.bakery_number " +
            "WHERE M.bakery_number = ?1 ",
            nativeQuery = true)
    List<GetBakeryResultSet> getBakery(Integer boardNumber);

    @Query(value = "SELECT " +
            "B.id AS bakeryId, " +
            "B.title AS title, " +
            "B.mapx AS mapx, " +
            "B.mapy AS mapy " +
            "FROM bakery AS B " +
            "ORDER BY RAND() " +
            "LIMIT 100 ",
            nativeQuery = true
    )
    List<BakeryMarkerListItem> getRandomMarkerLimit100();
}
