package com.jino.realbread.domain.bakery.repository;

import com.jino.realbread.domain.bakery.repository.resultSet.BakeryMarkerListItem;
import com.jino.realbread.domain.bakery.entity.Bakery;
import com.jino.realbread.domain.bakery.repository.resultSet.GetBakeryMainListItemResultSet;
import com.jino.realbread.domain.bakery.repository.resultSet.GetBakeryResultSet;
import com.jino.realbread.menu.crawler.BakeryCrawlDto;

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
                        "LEFT JOIN menu AS M " +
                        "ON B.id = M.bakery_number " +
                        "WHERE B.id = ?1 ", nativeQuery = true)
        List<GetBakeryResultSet> getBakery(Integer boardNumber);

        @Query(value = "SELECT " +
                        "B.id AS bakeryNumber, " +
                        "B.title AS title, " +
                        "B.mapx / 10000000.0 AS mapx, " +
                        "B.mapy / 10000000.0 AS mapy " +
                        "FROM bakery AS B " +
                        "ORDER BY RAND() " +
                        "LIMIT 100 ", nativeQuery = true)
        List<BakeryMarkerListItem> getRandomMarkerLimit100();

        @Query(value = "SELECT * " +
                        "FROM ( " +
                        "    SELECT " +
                        "        b.id AS bakeryNumber, " +
                        "        b.title AS title, " +
                        "        b.road_address AS roadAddress, " +
                        "        b.mapx / 10000000.0 AS mapx, " +
                        "        b.mapy / 10000000.0 AS mapy, " +
                        "        b.link AS link, " +
                        "        b.favorite_count AS favoriteCount, " +
                        "        b.comment_count AS commentCount, " +
                        "        m.id AS menuNumber, " +
                        "        m.image_url AS imageUrl, " +
                        "        ROW_NUMBER() OVER (PARTITION BY b.id ORDER BY m.id) AS rn " +
                        "    FROM ( " +
                        "        SELECT * FROM bakery ORDER BY RAND() LIMIT 100 " +
                        "    ) b " +
                        "    LEFT JOIN menu m ON b.id = m.bakery_number " +
                        ") ranked " +
                        "WHERE rn <= 4", nativeQuery = true)
        List<GetBakeryMainListItemResultSet> getBakeryMainList();

        Bakery findByBakeryId(Integer bakeryNumber);

        boolean existsByBakeryId(Integer bakeryNumber);

        @Query(value = """
                        SELECT *
                        FROM (
                                SELECT
                                b.id AS bakeryNumber,
                                b.title AS title,
                                b.road_address AS roadAddress,
                                b.mapx / 10000000.0 AS mapx,
                                b.mapy / 10000000.0 AS mapy,
                                b.link AS link,
                                b.favorite_count AS favoriteCount,
                                b.comment_count AS commentCount,
                                m.id AS menuNumber,
                                m.image_url AS imageUrl,
                                ROW_NUMBER() OVER (PARTITION BY b.id ORDER BY m.id) AS rn
                                FROM (
                                        SELECT * FROM bakery ORDER BY comment_count DESC LIMIT 100
                                ) b
                                LEFT JOIN menu m ON b.id = m.bakery_number
                        ) ranked
                        WHERE rn <= 4
                        ORDER BY commentCount DESC
                        """, nativeQuery = true)
        List<GetBakeryMainListItemResultSet> getBakeryMainListOrderByCommentCount();

        @Query(value = """
                        SELECT *
                        FROM (
                                SELECT
                                b.id AS bakeryNumber,
                                b.title AS title,
                                b.road_address AS roadAddress,
                                b.mapx / 10000000.0 AS mapx,
                                b.mapy / 10000000.0 AS mapy,
                                b.link AS link,
                                b.favorite_count AS favoriteCount,
                                b.comment_count AS commentCount,
                                m.id AS menuNumber,
                                m.image_url AS imageUrl,
                                ROW_NUMBER() OVER (PARTITION BY b.id ORDER BY m.id) AS rn
                                FROM (
                                        SELECT * FROM bakery ORDER BY favorite_count DESC LIMIT 100
                                ) b
                                LEFT JOIN menu m ON b.id = m.bakery_number
                        ) ranked
                        WHERE rn <= 4
                        ORDER BY favoriteCount DESC
                        """, nativeQuery = true)
        List<GetBakeryMainListItemResultSet> getBakeryMainListOrderByFavoriteCount();

        @Query(value = """
                        SELECT
                                b.id AS bakeryId,
                                b.title AS title,
                                b.address AS address
                        FROM bakery b
                        LEFT JOIN menu m ON b.id = m.bakery_number
                        WHERE m.id IS NULL
                        ORDER BY RAND()
                        """, nativeQuery = true)
        List<BakeryCrawlDto> findAllForCrawling();

        @Query(value = "SELECT id FROM bakery", nativeQuery = true)
        List<Long> findAllBakeryIds();
}
