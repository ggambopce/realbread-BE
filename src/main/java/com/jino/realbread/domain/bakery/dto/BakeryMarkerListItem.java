package com.jino.realbread.domain.bakery.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BakeryMarkerListItem {

    private Integer bakeryNumber;
    private String title;
    private String mapx;
    private String mapy;
}
