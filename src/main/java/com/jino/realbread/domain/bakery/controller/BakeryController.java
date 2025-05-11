package com.jino.realbread.domain.bakery.controller;

import com.jino.realbread.domain.bakery.dto.response.GetBakeryMainListResponseDto;
import com.jino.realbread.domain.bakery.dto.response.GetBakeryMarkerListResponseDto;
import com.jino.realbread.domain.bakery.dto.response.GetBakeryResponseDto;
import com.jino.realbread.domain.bakery.service.BakeryService;
import com.jino.realbread.naverapi.NaverSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/bakery")
@RequiredArgsConstructor
public class BakeryController {

    private final NaverSearchService naverSearchService;
    private final BakeryService bakeryService;

    @GetMapping("/search")
    public ResponseEntity<String> searchAndSave(@RequestParam String keyword) {
        naverSearchService.searchAndSave(keyword);
        return ResponseEntity.ok("저장 완료");
    }

    @GetMapping("/{bakeryNumber}")
    public ResponseEntity<? super GetBakeryResponseDto> getBakery(@PathVariable("bakeryNumber") Integer bakeryNumber) {
        ResponseEntity<? super GetBakeryResponseDto> response = bakeryService.getBakery(bakeryNumber);
        return response;
    }

    @GetMapping("/marker/random")
    public ResponseEntity<? super GetBakeryMarkerListResponseDto> getRandomMarkerList() {
        ResponseEntity<? super GetBakeryMarkerListResponseDto> response = bakeryService.getRandomMarkerList();
        return response;
    }

    @GetMapping("/main-list")
    public ResponseEntity<? super GetBakeryMainListResponseDto> getMainBakeryList() {
        return bakeryService.getMainBakeryList();
    }


}
