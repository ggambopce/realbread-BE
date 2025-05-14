package com.jino.realbread.domain.bakery.dto;

import java.util.ArrayList;
import java.util.List;
import com.jino.realbread.domain.view.BakeryListViewEntity;

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

    // Menu 정보
    private Integer menuId;
    private String menuName;
    private String menuPrice;
    private String menuImageUrl;
    private String menuDescription;

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

        this.menuId = entity.getMenuId();
        this.menuName = entity.getMenuName();
        this.menuPrice = entity.getMenuPrice();
        this.menuImageUrl = entity.getMenuImageUrl();
        this.menuDescription = entity.getMenuDescription();
    }

    // 리스트 변환 유틸
    public static List<BakeryListItem> getList(List<BakeryListViewEntity> entities) {

        List<BakeryListItem> list = new ArrayList<>();
        for (BakeryListViewEntity bakeryListViewEntity : entities) {
            BakeryListItem boBakeryListItem = new BakeryListItem(bakeryListViewEntity);
            list.add(boBakeryListItem);
        }
        return list;
    }
}
