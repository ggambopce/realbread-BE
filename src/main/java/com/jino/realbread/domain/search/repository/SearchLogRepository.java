package com.jino.realbread.domain.search.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jino.realbread.domain.search.entity.SearchLogEntity;

@Repository
public interface SearchLogRepository extends JpaRepository<SearchLogEntity, Integer> {

}
