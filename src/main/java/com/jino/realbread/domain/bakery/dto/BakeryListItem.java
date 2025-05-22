package com.jino.realbread.domain.bakery.dto;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.jino.realbread.domain.view.BakeryListViewEntity;
import com.jino.realbread.menu.dto.MenuListItem;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BakeryListItem {

    // Bakery 정보
    private Integer bakeryNumber;
    private String title;
    private String roadAddress;
    private String link;
    private String mapx;
    private String mapy;
    private int fvoriteCount;
    private int commentCount;

    // 메뉴 리스트(상세, 검색 모두 사용)
    private List<MenuListItem> menuList = new ArrayList<>();

    // Bakery 정보만 초기화하는 생성자
    public BakeryListItem(BakeryListViewEntity entity) {
        this.bakeryNumber = entity.getBakeryNumber();
        this.title = entity.getTitle();
        this.roadAddress = entity.getRoadAddress();
        this.link = entity.getLink();
        this.mapx = entity.getMapx();
        this.mapy = entity.getMapy();
        this.fvoriteCount = entity.getFavoriteCount();
        this.commentCount = entity.getCommentCount();
    }

    // 메뉴 추가 메서드
    public void addMenu(BakeryListViewEntity entity) {
        this.menuList.add(new MenuListItem(
                entity.getMenuNumber(),
                entity.getMenuName(),
                entity.getPrice(),
                entity.getDescription(),
                entity.getImageUrl()));
    }

    // Bakery ID 기준으로 중복 제거 및 메뉴 통합
    public static List<BakeryListItem> getList(List<BakeryListViewEntity> entities) {
        Map<Integer, BakeryListItem> bakeryMap = new LinkedHashMap<>();

        for (BakeryListViewEntity entity : entities) {
            Integer bakeryId = entity.getBakeryNumber();
            BakeryListItem item = bakeryMap.get(bakeryId);

            if (item == null) {
                item = new BakeryListItem(entity);
                bakeryMap.put(bakeryId, item);
            }

            item.addMenu(entity);
        }

        return new ArrayList<>(bakeryMap.values());
    }
}
