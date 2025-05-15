package com.jino.realbread.domain.bakery.controller;

import com.jino.realbread.domain.bakery.dto.response.GetBakeryMainListResponseDto;
import com.jino.realbread.domain.bakery.dto.response.GetBakeryMarkerListResponseDto;
import com.jino.realbread.domain.bakery.dto.response.GetBakeryResponseDto;
import com.jino.realbread.domain.bakery.dto.response.GetSearchBakeryListResponseDto;
import com.jino.realbread.domain.bakery.service.BakeryService;
import com.jino.realbread.domain.favorite.dto.response.GetFavoriteListResponseDto;
import com.jino.realbread.domain.favorite.dto.response.PutFavoriteResponseDto;
import com.jino.realbread.naverapi.NaverSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    @GetMapping("/detail/{bakeryNumber}")
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
    public ResponseEntity<? super GetBakeryMainListResponseDto> getMainBakeryList(
            @RequestParam(value = "sort", required = false, defaultValue = "") String sort) {
        return bakeryService.getMainBakeryList(sort);
    }

    @PutMapping("/{boardNumber}/favorite")
    public ResponseEntity<? super PutFavoriteResponseDto> putFavorite(@PathVariable("boardNumber") Integer boardNumber,
            @AuthenticationPrincipal Integer userId) {
        ResponseEntity<? super PutFavoriteResponseDto> response = bakeryService.putFavorite(boardNumber, userId);
        return response;
    }

    @GetMapping(value = { "/search-list/{searchWord}", "/search-list/{searchWord}/{preSearchWord}" })
    public ResponseEntity<? super GetSearchBakeryListResponseDto> getSearchBakeryList(
            @PathVariable("searchWord") String searchWord,
            @PathVariable(value = "preSearchWord", required = false) String preSearchWord) {
        ResponseEntity<? super GetSearchBakeryListResponseDto> response = bakeryService.getSearchBakeryList(searchWord,
                preSearchWord);
        return response;
    }

    @GetMapping("/{bakeryNumber}/favorite-list")
    public ResponseEntity<? super GetFavoriteListResponseDto> getFavoriteList(
            @PathVariable("bakeryNumber") Integer bakeryNumber) {
        ResponseEntity<? super GetFavoriteListResponseDto> response = bakeryService.getFavoriteList(bakeryNumber);
        return response;
    }

}
