package com.jino.realbread.naverapi;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NaverSearchBakery {
    private String title;
    private String category;
    private String address;
    private String roadAddress;
    private String link;
    private String mapx;
    private String mapy;
}
