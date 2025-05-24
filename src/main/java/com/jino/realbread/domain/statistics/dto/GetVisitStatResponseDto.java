package com.jino.realbread.domain.statistics.dto;

import java.time.LocalDate;
import java.util.List;

import com.jino.realbread.domain.statistics.entity.VisitStat;
import com.jino.realbread.global.common.ResponseCode;
import com.jino.realbread.global.common.ResponseMessage;
import com.jino.realbread.global.dto.response.ResponseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class GetVisitStatResponseDto extends ResponseDto {

    private Long bakeryId;
    private Long uniqueVisitCountNow; // 레디스 고유방문자수
    private Long yesterdayVisitCount; // DB 어제 고유방문자수로 계산
    private List<Daily> weeklyStats; // DB 날짜, uniqueVisitCount
    private Long increaseFromYesterday; // DB 어제 고유방문자수와 레디스 현재 고유방문자수로 계산

    @Getter
    @AllArgsConstructor
    public static class Daily {
        private LocalDate date;
        private Long count;
    }

    public GetVisitStatResponseDto(Long bakeryId,
            Long uniqueVisitCountNow,
            Long yesterdayVisitCount,
            List<Daily> weeklyStats,
            Long increaseFromYesterday) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.bakeryId = bakeryId;
        this.uniqueVisitCountNow = uniqueVisitCountNow;
        this.yesterdayVisitCount = yesterdayVisitCount;
        this.weeklyStats = weeklyStats;
        this.increaseFromYesterday = increaseFromYesterday;

    }

}
