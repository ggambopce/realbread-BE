package com.jino.realbread.domain.statistics.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jino.realbread.domain.statistics.entity.VisitStat;

@Repository
public interface VisitStatRepository extends JpaRepository<VisitStat, Long> {

    List<VisitStat> findByBakeryIdAndDateBetween(Long bakeryId, LocalDate startDate, LocalDate endDate);
}
