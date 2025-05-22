package com.jino.realbread.domain.statistics.dto;

import java.time.LocalDate;

import com.jino.realbread.domain.statistics.entity.VisitStat;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class VisitStatResponseDto {

    private LocalDate date;
    private Long totalVisitCount;
    private Long uniqueVisitCount;

    public static VisitStatResponseDto from(VisitStat entity) {
        return new VisitStatResponseDto(
                entity.getDate(),
                entity.getTotalVisitCount(),
                entity.getUniqueVisitCount());
    }
}
