package com.jino.realbread.menu;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class MenuDto {

    private String menuName;
    private String price;
    private String imageUrl;
    private String description;
}
