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
    private Integer bakeryId;
    private String bakeryTitle;
    private String bakeryCategory;
    private String bakeryAddress;
    private String bakeryRoadAddress;
    private String bakeryLink;
    private String bakeryMapx;
    private String bakeryMapy;
    private int bakeryFavoriteCount;
    private int bakeryCommentCount;

    // 메뉴 리스트
    private List<MenuListItem> menuList = new ArrayList<>();

    // Bakery 정보만 초기화하는 생성자
    public BakeryListItem(BakeryListViewEntity entity) {
        this.bakeryId = entity.getBakeryId();
        this.bakeryTitle = entity.getBakeryTitle();
        this.bakeryCategory = entity.getBakeryCategory();
        this.bakeryAddress = entity.getBakeryAddress();
        this.bakeryRoadAddress = entity.getBakeryRoadAddress();
        this.bakeryLink = entity.getBakeryLink();
        this.bakeryMapx = entity.getBakeryMapx();
        this.bakeryMapy = entity.getBakeryMapy();
        this.bakeryFavoriteCount = entity.getBakeryFavoriteCount();
        this.bakeryCommentCount = entity.getBakeryCommentCount();
    }

    // 메뉴 추가 메서드
    public void addMenu(BakeryListViewEntity entity) {
        this.menuList.add(new MenuListItem(
                entity.getMenuId(),
                entity.getMenuName(),
                entity.getMenuPrice(),
                entity.getMenuDescription(),
                entity.getMenuImageUrl()));
    }

    // Bakery ID 기준으로 중복 제거 및 메뉴 통합
    public static List<BakeryListItem> getList(List<BakeryListViewEntity> entities) {
        Map<Integer, BakeryListItem> bakeryMap = new LinkedHashMap<>();

        for (BakeryListViewEntity entity : entities) {
            Integer bakeryId = entity.getBakeryId();
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
