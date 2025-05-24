package com.jino.realbread.domain.statistics.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jino.realbread.domain.statistics.dto.GetVisitStatResponseDto;
import com.jino.realbread.domain.statistics.service.VisitStatService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bakery")
public class VisitStatController {

    private final VisitStatService visitStatService;

    @GetMapping("/{bakeryId}/visit-stats")
    public ResponseEntity<GetVisitStatResponseDto> getVisitStats(@PathVariable Long bakeryId) {
        GetVisitStatResponseDto response = visitStatService.getVisitStats(bakeryId);
        return ResponseEntity.ok(response);
    }
}