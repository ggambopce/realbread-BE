package com.jino.realbread.domain.statistics.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.jino.realbread.domain.bakery.repository.BakeryRepository;
import com.jino.realbread.domain.statistics.dto.GetVisitStatResponseDto;
import com.jino.realbread.domain.statistics.entity.VisitStat;
import com.jino.realbread.domain.statistics.repository.VisitStatRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class VisitStatBatchScheduler {

    private final RedisTemplate<String, String> redisTemplate;
    private final VisitStatRepository visitStatRepository;
    private final BakeryRepository bakeryRepository;

    @Scheduled(cron = "0 0 1 * * *") // 매일 1시 실행
    public void flushVisitStatToDB() {
        String date = LocalDate.now().minusDays(1).format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        List<Long> bakeryIds = bakeryRepository.findAllBakeryIds();

        for (Long bakeryId : bakeryIds) {
            String pvKey = "visit:count:" + date + ":" + bakeryId;
            String uvKey = "visit:uv:" + date + ":" + bakeryId;

            String totalVisitStr = redisTemplate.opsForValue().get(pvKey);
            Long uniqueVisitCount = redisTemplate.opsForSet().size(uvKey); // or pfCount

            if (totalVisitStr != null) {
                Long totalVisitCount = Long.parseLong(totalVisitStr);
                VisitStat stat = new VisitStat(
                        null,
                        bakeryId,
                        LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyyMMdd")),
                        totalVisitCount,
                        uniqueVisitCount != null ? uniqueVisitCount : 0L);
                visitStatRepository.save(stat);
            }
        }
    }

    // 실시간 오늘 고유 방문자수
    public Long getTodayUniqueVisitCount(Long bakeryId) {
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String key = "visit:uv:" + today + ":" + bakeryId;
        Long count = redisTemplate.opsForSet().size(key);
        return count != null ? count : 0L;
    }

}
