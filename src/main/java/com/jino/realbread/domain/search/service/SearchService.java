package com.jino.realbread.domain.search.service;

import org.springframework.http.ResponseEntity;

import com.jino.realbread.domain.search.dto.GetPopularListResponseDto;
import com.jino.realbread.domain.search.dto.GetRelationListResponseDto;

public interface SearchService {

    ResponseEntity<? super GetPopularListResponseDto> getPopularList();

    ResponseEntity<? super GetRelationListResponseDto> getRelationList(String searchWord);
}
