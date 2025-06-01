package com.jino.realbread.domain.bakery.controller;

import com.jino.realbread.domain.bakery.dto.response.GetBakeryMainListResponseDto;
import com.jino.realbread.domain.bakery.dto.response.GetBakeryMarkerListResponseDto;
import com.jino.realbread.domain.bakery.dto.response.GetBakeryResponseDto;
import com.jino.realbread.domain.bakery.service.BakeryService;
import com.jino.realbread.domain.favorite.dto.response.GetFavoriteListResponseDto;
import com.jino.realbread.domain.favorite.dto.response.PutFavoriteResponseDto;
import com.jino.realbread.global.security.auth.PrincipalDetails;
import com.jino.realbread.naverapi.NaverSearchService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = { "http://localhost:5173",
        "https://realbread.jinorandb.com" })
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
    public ResponseEntity<? super GetBakeryResponseDto> getBakery(@PathVariable("bakeryNumber") Integer bakeryNumber,
            HttpServletRequest request) {
        ResponseEntity<? super GetBakeryResponseDto> response = bakeryService.getBakery(bakeryNumber, request);
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
            @AuthenticationPrincipal PrincipalDetails principalDetails) {
        ResponseEntity<? super PutFavoriteResponseDto> response = bakeryService.putFavorite(boardNumber,
                principalDetails);
        return response;
    }

    @GetMapping(value = { "/search-list/{searchWord}", "/search-list/{searchWord}/{preSearchWord}" })
    public ResponseEntity<? super GetBakeryMainListResponseDto> getSearchBakeryList(
            @PathVariable("searchWord") String searchWord,
            @PathVariable(value = "preSearchWord", required = false) String preSearchWord) {
        ResponseEntity<? super GetBakeryMainListResponseDto> response = bakeryService.getSearchBakeryList(searchWord,
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
