package com.jino.realbread.menu.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MenuListItem {

    private int menuNumber;
    private String menuName;
    private String price;
    private String description;
    private String imageUrl;
}
