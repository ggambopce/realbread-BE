package com.jino.realbread.domain.bakery.service;

import com.jino.realbread.domain.bakery.dto.response.GetBakeryMainListResponseDto;
import com.jino.realbread.domain.bakery.dto.response.GetBakeryMarkerListResponseDto;
import com.jino.realbread.domain.bakery.dto.response.GetBakeryResponseDto;
import com.jino.realbread.domain.bakery.dto.response.GetSearchBakeryListResponseDto;
import com.jino.realbread.domain.favorite.dto.response.GetFavoriteListResponseDto;
import com.jino.realbread.domain.favorite.dto.response.PutFavoriteResponseDto;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;

public interface BakeryService {

    ResponseEntity<? super GetBakeryResponseDto> getBakery(Integer bakeryNumber, HttpServletRequest request);

    ResponseEntity<? super GetBakeryMarkerListResponseDto> getRandomMarkerList();

    ResponseEntity<? super GetBakeryMainListResponseDto> getMainBakeryList(String sort);

    ResponseEntity<? super GetBakeryMainListResponseDto> getSearchBakeryList(String searchWord, String preSearchWord);

    ResponseEntity<? super GetFavoriteListResponseDto> getFavoriteList(Integer bakeryNumber);

    ResponseEntity<? super PutFavoriteResponseDto> putFavorite(Integer bakeryNumber, Integer userId);
}
