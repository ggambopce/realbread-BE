package com.jino.realbread.domain.statistics.service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.jino.realbread.domain.statistics.dto.GetVisitStatResponseDto;
import com.jino.realbread.domain.statistics.dto.GetVisitStatResponseDto.Daily;
import com.jino.realbread.domain.statistics.entity.VisitStat;
import com.jino.realbread.domain.statistics.repository.VisitStatProjection;
import com.jino.realbread.domain.statistics.repository.VisitStatRepository;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VisitStatService {

    private final VisitStatRepository visitStatRepository;
    private final RedisTemplate<String, String> redisTemplate;
    private final VisitStatBatchScheduler visitStatBatchScheduler;

    // 방문 기록 DB 저장
    public void recordVisit(Integer bakeryId, HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        String pvKey = "visit:count:" + today + ":" + bakeryId;
        String uvKey = "visit:uv:" + today + ":" + bakeryId;

        redisTemplate.opsForValue().increment(pvKey);
        redisTemplate.opsForSet().add(uvKey, ip);

        redisTemplate.expire(pvKey, Duration.ofDays(2));
        redisTemplate.expire(uvKey, Duration.ofDays(2));

    }

    public GetVisitStatResponseDto getVisitStats(Long bakeryId) {
        LocalDate today = LocalDate.now();
        LocalDate yesterday = today.minusDays(1);
        LocalDate weekAgo = today.minusDays(6);

        // 오늘 방문자 수 (Redis)
        Long uniqueVisitCountNow = visitStatBatchScheduler.getTodayUniqueVisitCount(bakeryId);

        // 어제 방문자 수 (DB)
        Long yesterdayVisitCount = Optional.ofNullable(
                visitStatRepository.getYesterdayVisitCountByBakeryId(bakeryId, yesterday)).orElse(0L);

        // 최근 7일 방문 통계 (DB)
        List<VisitStatProjection> rawStats = visitStatRepository.getWeeklyStats(bakeryId, weekAgo, today);

        // 어제 대비 증가량
        Long increaseFromYesterday = uniqueVisitCountNow - yesterdayVisitCount;

        List<Daily> weeklyStats = rawStats.stream()
                .map(p -> new Daily(p.getDate(), p.getUniqueVisitCount()))
                .collect(Collectors.toList());

        return new GetVisitStatResponseDto(
                bakeryId,
                uniqueVisitCountNow,
                yesterdayVisitCount,
                weeklyStats,
                increaseFromYesterday);

    }

}
