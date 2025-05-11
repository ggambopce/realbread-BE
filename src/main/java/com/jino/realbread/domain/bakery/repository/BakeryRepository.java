package com.jino.realbread.domain.bakery.repository;

import com.jino.realbread.domain.bakery.repository.resultSet.BakeryMarkerListItem;
import com.jino.realbread.domain.bakery.entity.Bakery;
import com.jino.realbread.domain.bakery.repository.resultSet.GetBakeryMainListItemResultSet;
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
            "B.id AS bakeryNumber, " +
            "B.title AS title, " +
            "B.mapx / 10000000.0 AS mapx, " +
            "B.mapy / 10000000.0 AS mapy " +
            "FROM bakery AS B " +
            "ORDER BY RAND() " +
            "LIMIT 100 ",
            nativeQuery = true
    )
    List<BakeryMarkerListItem> getRandomMarkerLimit100();

    @Query(value = "SELECT " +
            "* " +
            "FROM ( " +
                "SELECT " +
                "b.id AS bakeryNumber, " +
                "b.title AS title, " +
                "b.favorite_count AS favoriteCount, " +
                "b.comment_count AS commentCount, " +
                "m.id AS menuNumber, " +
                "m.image_url AS imageUrl, " +
                "ROW_NUMBER() OVER (PARTITION BY b.id ORDER BY m.id) AS rn " +
                "FROM bakery b " +
                "LEFT JOIN menu m ON b.id = m.bakery_number " +
                ") ranked " +
            "WHERE rn <= 4",
            nativeQuery = true
    )
    List<GetBakeryMainListItemResultSet> getBakeryMainList();
}
