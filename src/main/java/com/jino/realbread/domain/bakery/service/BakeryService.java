package com.jino.realbread.domain.bakery.service;

import com.jino.realbread.domain.bakery.dto.response.GetBakeryMainListResponseDto;
import com.jino.realbread.domain.bakery.dto.response.GetBakeryMarkerListResponseDto;
import com.jino.realbread.domain.bakery.dto.response.GetBakeryResponseDto;
import com.jino.realbread.domain.bakery.dto.response.GetSearchBakeryListResponseDto;

import org.springframework.http.ResponseEntity;

public interface BakeryService {

    ResponseEntity<? super GetBakeryResponseDto> getBakery(Integer BakeryNumber);

    ResponseEntity<? super GetBakeryMarkerListResponseDto> getRandomMarkerList();

    ResponseEntity<? super GetBakeryMainListResponseDto> getMainBakeryList(String sort);

    ResponseEntity<? super GetSearchBakeryListResponseDto> getSearchBakeryList(String searchWord, String preSearchWord);
}
