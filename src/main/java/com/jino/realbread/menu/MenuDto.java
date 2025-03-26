package com.jino.realbread.menu;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MenuDto {

    private String menuName;
    private String price;
    private String imageUrl;
    private String description;
}
