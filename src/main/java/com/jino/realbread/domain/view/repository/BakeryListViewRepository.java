package com.jino.realbread.domain.view.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.jino.realbread.domain.view.BakeryListViewEntity;

@Repository
public interface BakeryListViewRepository extends JpaRepository<BakeryListViewEntity, Integer> {

    @Query(value = """
            SELECT *
            FROM (
                SELECT *,
                       ROW_NUMBER() OVER (PARTITION BY bakery_id ORDER BY menu_id) AS rn
                FROM bakery_list_view
                WHERE bakery_title LIKE CONCAT('%', :searchWord, '%')
                   OR bakery_address LIKE CONCAT('%', :searchWord, '%')
            ) sub
            WHERE rn <= 4
            """, nativeQuery = true)
    List<BakeryListViewEntity> getSearchByBakeryTitleOrBakeryAddress(String searchWord);

}
