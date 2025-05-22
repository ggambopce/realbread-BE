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
                    b.id AS bakery_list_view_id,
                    b.id AS bakery_id,
                    b.title AS bakery_title,
                    b.road_address AS bakery_road_address,
                    b.link AS bakery_link,
                    b.mapx / 10000000.0 AS bakery_mapx,
                    b.mapy / 10000000.0 AS bakery_mapy,
                    b.favorite_count AS bakery_favorite_count,
                    b.comment_count AS bakery_comment_count,
                    m.id AS menu_id,
                    m.menu_name AS menu_name,
                    m.price AS menu_price,
                    m.description AS menu_description,
                    m.image_url AS menu_image_url,
                    ROW_NUMBER() OVER (PARTITION BY b.id ORDER BY m.id) AS rn
                FROM bakery b
                LEFT JOIN menu m ON b.id = m.bakery_number
                WHERE b.title LIKE CONCAT('%', :searchWord, '%')
                OR b.address LIKE CONCAT('%', :searchWord, '%')
            ) sub
            WHERE rn <= 4
            """, nativeQuery = true)
    List<BakeryListViewEntity> getSearchByBakeryTitleOrBakeryAddress(String searchWord);

}
