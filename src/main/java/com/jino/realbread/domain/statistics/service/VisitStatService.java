package com.jino.realbread.domain.statistics.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.jino.realbread.domain.statistics.dto.VisitStatResponseDto;
import com.jino.realbread.domain.statistics.entity.VisitStat;
import com.jino.realbread.domain.statistics.repository.VisitStatRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VisitStatService {

    private final VisitStatRepository visitStatRepository;

    public List<VisitStatResponseDto> getLast7DaysStats(Long bakeryId) {
        LocalDate today = LocalDate.now();
        LocalDate from = today.minusDays(6);
        List<VisitStat> stats = visitStatRepository.findByBakeryIdAndDateBetween(bakeryId, from, today);
        return stats.stream()
                .map(VisitStatResponseDto::from)
                .toList();
    }
}
