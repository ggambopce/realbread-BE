package com.jino.realbread.domain.bakery.service.implement;

import com.jino.realbread.domain.bakery.dto.BakeryListItem;
import com.jino.realbread.domain.bakery.dto.response.GetBakeryMainListResponseDto;
import com.jino.realbread.domain.bakery.repository.resultSet.BakeryMarkerListItem;
import com.jino.realbread.domain.bakery.dto.response.GetBakeryMarkerListResponseDto;
import com.jino.realbread.domain.bakery.dto.response.GetBakeryResponseDto;
import com.jino.realbread.domain.bakery.dto.response.GetSearchBakeryListResponseDto;
import com.jino.realbread.domain.bakery.repository.BakeryRepository;
import com.jino.realbread.domain.bakery.repository.resultSet.GetBakeryMainListItemResultSet;
import com.jino.realbread.domain.bakery.repository.resultSet.GetBakeryResultSet;
import com.jino.realbread.domain.bakery.service.BakeryService;
import com.jino.realbread.domain.search.entity.SearchLogEntity;
import com.jino.realbread.domain.search.repository.SearchLogRepository;
import com.jino.realbread.domain.view.BakeryListViewEntity;
import com.jino.realbread.domain.view.repository.BakeryListViewRepository;
import com.jino.realbread.global.dto.response.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BakeryServiceImplement implements BakeryService {

    private final BakeryRepository bakeryRepository;
    private final SearchLogRepository searchLogRepository;
    private final BakeryListViewRepository bakeryListViewRepository;

    @Override
    public ResponseEntity<? super GetBakeryResponseDto> getBakery(Integer bakeryNumber) {

        List<GetBakeryResultSet> resultSet;

        try {
            resultSet = bakeryRepository.getBakery(bakeryNumber);
            if (resultSet.isEmpty())
                return GetBakeryResponseDto.noExistBakery();

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return GetBakeryResponseDto.success(resultSet);
    }

    @Override
    public ResponseEntity<? super GetBakeryMarkerListResponseDto> getRandomMarkerList() {

        List<BakeryMarkerListItem> markerList;

        try {
            markerList = bakeryRepository.getRandomMarkerLimit100();
            if (markerList.isEmpty())
                return GetBakeryMarkerListResponseDto.noExistMarker();
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return GetBakeryMarkerListResponseDto.success(markerList);

    }

    @Override
    public ResponseEntity<? super GetBakeryMainListResponseDto> getMainBakeryList() {

        List<GetBakeryMainListItemResultSet> resultSets;

        try {
            resultSets = bakeryRepository.getBakeryMainList();

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return GetBakeryMainListResponseDto.success(resultSets);
    }

    @Override
    public ResponseEntity<? super GetSearchBakeryListResponseDto> getSearchBakeryList(String searchWord,
            String preSearchWord) {
        List<BakeryListViewEntity> bakeryListViewEntities = new ArrayList<>();

        try {

            bakeryListViewEntities = bakeryListViewRepository
                    .findByBakeryTitleContainsOrBakeryRoadAddressContainsOrBakeryAddressContainsOrMenuNameContains(
                            searchWord, searchWord);

            SearchLogEntity searchLogEntity = new SearchLogEntity(searchWord, preSearchWord, false);
            searchLogRepository.save(searchLogEntity);

            boolean relation = preSearchWord != null;
            if (relation) {
                searchLogEntity = new SearchLogEntity(preSearchWord, searchWord, relation);
                searchLogRepository.save(searchLogEntity);
            }

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return GetSearchBakeryListResponseDto.success(bakeryListViewEntities);
    }

}