package com.jino.realbread.menu.dto;

import com.jino.realbread.domain.view.BakeryListViewEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MenuListItem {

    private Integer menuNumber;
    private String menuName;
    private String price;
    private String description;
    private String imageUrl;

    public MenuListItem(BakeryListViewEntity entity) {
        this.menuNumber = entity.getMenuId();
        this.menuName = entity.getMenuName();
        this.price = entity.getMenuPrice();
        this.description = entity.getMenuDescription();
        this.imageUrl = entity.getMenuImageUrl();
    }
}
