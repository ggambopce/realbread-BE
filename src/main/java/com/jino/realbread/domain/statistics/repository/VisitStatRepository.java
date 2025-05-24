package com.jino.realbread.domain.statistics.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jino.realbread.domain.statistics.entity.VisitStat;

@Repository
public interface VisitStatRepository extends JpaRepository<VisitStat, Long> {

    // 어제 고유 방문자 수 조회
    @Query("""
                SELECT vs.uniqueVisitCount
                FROM VisitStat vs
                WHERE vs.bakeryId = :bakeryId
                AND vs.date = :yesterday
            """)
    Long getYesterdayVisitCountByBakeryId(@Param("bakeryId") Long bakeryId,
            @Param("yesterday") LocalDate yesterday);

    // 최근 7일간 고유 방문자 수 통계 (날짜 ASC 정렬)
    @Query(value = """
                SELECT
                    date AS date,
                    unique_visit_count AS uniqueVisitCount
                FROM visit_stat
                WHERE bakery_id = :bakeryId
                AND date BETWEEN :start AND :end
                ORDER BY date ASC
            """, nativeQuery = true)
    List<VisitStatProjection> getWeeklyStats(@Param("bakeryId") Long bakeryId,
            @Param("start") LocalDate weekAgo,
            @Param("end") LocalDate today);

}
