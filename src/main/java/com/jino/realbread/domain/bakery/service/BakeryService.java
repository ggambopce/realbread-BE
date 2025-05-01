package com.jino.realbread.domain.bakery.service;

import com.jino.realbread.domain.bakery.dto.response.GetBakeryResponseDto;
import org.springframework.http.ResponseEntity;

public interface BakeryService {

    ResponseEntity<? super GetBakeryResponseDto> getBakery(Integer BakeryNumber);
}
