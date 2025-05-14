package com.jino.realbread.domain.search.service;

import org.springframework.http.ResponseEntity;

import com.jino.realbread.domain.search.dto.GetPopularListResponseDto;

public interface SearchService {

    ResponseEntity<? super GetPopularListResponseDto> getPopularList();
}
