package com.jino.realbread.domain.bakery.dto.response;

import com.jino.realbread.menu.dto.MenuListItem;
import lombok.Getter;

import java.util.List;

@Getter
public class GetBakeryMainListResponseDto {

    private Integer bakeryNumber;
    private String title;
    private Integer favoriteCount;
    private Integer commentCount;
    private List<MenuListItem> menuList;

}
