package com.jino.realbread.domain.view.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.jino.realbread.domain.bakery.repository.resultSet.GetBakeryMainListItemResultSet;
import com.jino.realbread.domain.view.BakeryListViewEntity;

@Repository
public interface BakeryListViewRepository extends JpaRepository<BakeryListViewEntity, Integer> {

    @Query(value = """
                SELECT *
                FROM (
                    SELECT
                        b.id AS bakeryNumber,
                        b.title AS title,
                        b.road_address AS roadAddress,
                        b.link AS link,
                        b.mapx / 10000000.0 AS mapx,
                        b.mapy / 10000000.0 AS mapy,
                        b.favorite_count AS favoriteCount,
                        b.comment_count AS commentCount,
                        m.id AS menuNumber,
                        m.image_url AS imageUrl,
                        ROW_NUMBER() OVER (PARTITION BY b.id ORDER BY m.id) AS rn
                    FROM bakery b
                    LEFT JOIN menu m ON b.id = m.bakery_number
                    WHERE b.title LIKE CONCAT('%', :searchWord, '%')
                    OR b.address LIKE CONCAT('%', :searchWord, '%')
                ) sub
                WHERE rn <= 4
            """, nativeQuery = true)
    List<GetBakeryMainListItemResultSet> getSearchByBakeryTitleOrBakeryAddress(String searchWord);

}
