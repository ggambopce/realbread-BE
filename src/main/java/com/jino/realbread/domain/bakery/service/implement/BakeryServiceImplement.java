package com.jino.realbread.domain.bakery.service.implement;

import com.jino.realbread.domain.bakery.dto.response.GetBakeryResponseDto;
import com.jino.realbread.domain.bakery.repository.BakeryRepository;
import com.jino.realbread.domain.bakery.repository.resultSet.GetBakeryResultSet;
import com.jino.realbread.domain.bakery.service.BakeryService;
import com.jino.realbread.global.dto.response.ResponseDto;
import com.jino.realbread.menu.Menu;
import com.jino.realbread.menu.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BakeryServiceImplement implements BakeryService {

    private final BakeryRepository bakeryRepository;

    @Override
    public ResponseEntity<? super GetBakeryResponseDto> getBakery(Integer bakeryNumber) {

        List<GetBakeryResultSet> resultSet;

        try {
            resultSet = bakeryRepository.getBakery(bakeryNumber);
            if (resultSet.isEmpty()) return GetBakeryResponseDto.noExistBakery();


        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return GetBakeryResponseDto.success(resultSet);
    }


}