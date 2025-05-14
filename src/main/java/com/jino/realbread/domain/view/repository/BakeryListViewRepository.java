package com.jino.realbread.domain.view.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jino.realbread.domain.view.BakeryListViewEntity;

@Repository
public interface BakeryListViewRepository extends JpaRepository<BakeryListViewEntity, Integer> {

    List<BakeryListViewEntity> findByBakeryTitleContainsOrBakeryRoadAddressContainsOrBakeryAddressContainsOrMenuNameContains(
            String searchWord,
            String searchWord2);

}
