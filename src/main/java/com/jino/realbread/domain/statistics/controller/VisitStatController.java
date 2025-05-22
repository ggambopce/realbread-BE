package com.jino.realbread.domain.statistics.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.jino.realbread.domain.statistics.dto.VisitStatResponseDto;
import com.jino.realbread.domain.statistics.service.VisitStatService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bakery")
public class VisitStatController {

    private final VisitStatService visitStatService;

    @GetMapping("/{bakeryId}/visit-stats")
    public List<VisitStatResponseDto> getVisitStats(@PathVariable Long bakeryId) {
        return visitStatService.getLast7DaysStats(bakeryId);
    }
}