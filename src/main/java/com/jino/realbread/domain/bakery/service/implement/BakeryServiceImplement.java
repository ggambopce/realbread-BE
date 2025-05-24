package com.jino.realbread.domain.bakery.service.implement;

import com.jino.realbread.domain.bakery.dto.BakeryListItem;
import com.jino.realbread.domain.bakery.dto.response.GetBakeryMainListResponseDto;
import com.jino.realbread.domain.bakery.repository.resultSet.BakeryMarkerListItem;
import com.jino.realbread.domain.bakery.dto.response.GetBakeryMarkerListResponseDto;
import com.jino.realbread.domain.bakery.dto.response.GetBakeryResponseDto;
import com.jino.realbread.domain.bakery.dto.response.GetSearchBakeryListResponseDto;
import com.jino.realbread.domain.bakery.entity.Bakery;
import com.jino.realbread.domain.bakery.repository.BakeryRepository;
import com.jino.realbread.domain.bakery.repository.resultSet.GetBakeryMainListItemResultSet;
import com.jino.realbread.domain.bakery.repository.resultSet.GetBakeryResultSet;
import com.jino.realbread.domain.bakery.service.BakeryService;
import com.jino.realbread.domain.favorite.dto.response.GetFavoriteListResponseDto;
import com.jino.realbread.domain.favorite.dto.response.PutFavoriteResponseDto;
import com.jino.realbread.domain.favorite.entity.FavoriteEntity;
import com.jino.realbread.domain.favorite.repository.FavoriteRepository;
import com.jino.realbread.domain.favorite.repository.resultSet.GetFavoriteListResultSet;
import com.jino.realbread.domain.search.entity.SearchLogEntity;
import com.jino.realbread.domain.search.repository.SearchLogRepository;
import com.jino.realbread.domain.statistics.service.VisitStatService;
import com.jino.realbread.domain.user.repository.UserRepository;
import com.jino.realbread.domain.view.BakeryListViewEntity;
import com.jino.realbread.domain.view.repository.BakeryListViewRepository;
import com.jino.realbread.global.dto.response.ResponseDto;

import jakarta.servlet.http.HttpServletRequest;
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
    private final FavoriteRepository favoriteRepository;
    private final UserRepository userRepository;
    private final VisitStatService visitStatService;

    @Override
    public ResponseEntity<? super GetBakeryResponseDto> getBakery(Integer bakeryNumber, HttpServletRequest request) {

        List<GetBakeryResultSet> resultSet;

        try {
            resultSet = bakeryRepository.getBakery(bakeryNumber);
            if (resultSet.isEmpty())
                return GetBakeryResponseDto.noExistBakery();

            visitStatService.recordVisit(bakeryNumber, request);

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
    public ResponseEntity<? super GetBakeryMainListResponseDto> getMainBakeryList(String sort) {

        List<GetBakeryMainListItemResultSet> resultSets;

        try {

            switch (sort) {
                case "review":
                    resultSets = bakeryRepository.getBakeryMainListOrderByCommentCount();
                    break;
                case "favorite":
                    resultSets = bakeryRepository.getBakeryMainListOrderByFavoriteCount();
                    break;
                default:
                    resultSets = bakeryRepository.getBakeryMainList(); // 기본 정렬 (예: 최신순)
            }

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return GetBakeryMainListResponseDto.success(resultSets);
    }

    @Override
    public ResponseEntity<? super GetBakeryMainListResponseDto> getSearchBakeryList(String searchWord,
            String preSearchWord) {
        List<GetBakeryMainListItemResultSet> resultSets = new ArrayList<>();

        try {

            resultSets = bakeryListViewRepository
                    .getSearchByBakeryTitleOrBakeryAddress(
                            searchWord);

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
        return GetBakeryMainListResponseDto.success(resultSets);
    }

    @Override
    public ResponseEntity<? super GetFavoriteListResponseDto> getFavoriteList(Integer bakeryNumber) {

        List<GetFavoriteListResultSet> resultSets = new ArrayList<>();

        try {

            boolean existedBakery = bakeryRepository.existsByBakeryId(bakeryNumber);
            if (!existedBakery)
                return GetFavoriteListResponseDto.noExistBakery();

            resultSets = favoriteRepository.getFavoriteList(bakeryNumber);

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return GetFavoriteListResponseDto.success(resultSets);
    }

    @Override
    public ResponseEntity<? super PutFavoriteResponseDto> putFavorite(Integer bakeryNumber, Integer userId) {

        try {

            boolean existedUser = userRepository.existsByUserId(userId);
            if (!existedUser)
                return PutFavoriteResponseDto.noExistUser();

            Bakery bakeryEntity = bakeryRepository.findByBakeryId(bakeryNumber);
            if (bakeryEntity == null)
                return PutFavoriteResponseDto.noExistBakery();

            FavoriteEntity favoriteEntity = favoriteRepository.findByBakeryNumberAndUserId(bakeryNumber, userId);
            if (favoriteEntity == null) {
                favoriteEntity = new FavoriteEntity(userId, bakeryNumber);
                favoriteRepository.save(favoriteEntity);
                bakeryEntity.increaseFavoriteCount();
            } else {
                favoriteRepository.delete(favoriteEntity);
                bakeryEntity.decreaseFavoriteCount();
            }

            bakeryRepository.save(bakeryEntity);

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return PutFavoriteResponseDto.success();
    }

}