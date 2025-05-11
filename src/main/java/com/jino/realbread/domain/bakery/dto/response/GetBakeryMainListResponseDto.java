package com.jino.realbread.domain.bakery.dto.response;

import com.jino.realbread.domain.bakery.repository.resultSet.BakeryMarkerListItem;
import com.jino.realbread.domain.bakery.repository.resultSet.GetBakeryMainListItemResultSet;
import com.jino.realbread.global.common.ResponseCode;
import com.jino.realbread.global.common.ResponseMessage;
import com.jino.realbread.global.dto.response.ResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class GetBakeryMainListResponseDto extends ResponseDto {

    private final List<BakerySummary> mainBakeryList;

    GetBakeryMainListResponseDto(List<BakerySummary> mainBakeryList) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.mainBakeryList = mainBakeryList;
    }

    public static ResponseEntity<GetBakeryMainListResponseDto> success(List<GetBakeryMainListItemResultSet> resultSet) {

        List<BakerySummary> mainBakeryList = new ArrayList<>();
        Map<Integer, List<GetBakeryMainListItemResultSet>> groupMap = new HashMap<>();

        for (GetBakeryMainListItemResultSet row : resultSet) {
            Integer bakeryNumber = row.getBakeryNumber();
            if (!groupMap.containsKey(bakeryNumber)) {
                groupMap.put(bakeryNumber, new ArrayList<>());
            }
            groupMap.get(bakeryNumber).add(row);
        }

        for (Integer bakeryNumber : groupMap.keySet()) {
            List<GetBakeryMainListItemResultSet> items = groupMap.get(bakeryNumber);
            GetBakeryMainListItemResultSet first = items.get(0);

            List<MenuListItem> menuList = new ArrayList<>();
            for (GetBakeryMainListItemResultSet item : items) {
                if (item.getMenuNumber() != null && item.getImageUrl() != null) {
                    menuList.add(new MenuListItem(item.getMenuNumber(), item.getImageUrl()));
                }
            }

            BakerySummary summary = new BakerySummary(
                    first.getBakeryNumber(),
                    first.getTitle(),
                    first.getFavoriteCount(),
                    first.getCommentCount(),
                    menuList
            );

            mainBakeryList.add(summary);
        }

        GetBakeryMainListResponseDto result = new GetBakeryMainListResponseDto(mainBakeryList);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    // 내부 클래스: 빵집 요약 정보
    @Getter
    @AllArgsConstructor
    public static class BakerySummary {
        private Integer bakeryNumber;
        private String title;
        private Integer favoriteCount;
        private Integer commentCount;
        private List<MenuListItem> menuList;
    }

    // 내부 클래스: 메뉴 요약 정보
    @Getter
    @AllArgsConstructor
    public static class MenuListItem {
        private Integer menuNumber;
        private String imageUrl;
    }

}
