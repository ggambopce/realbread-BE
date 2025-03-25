package com.jino.realbread.naverapi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class NaverSearchBakery {
    private String title;
    private String category;
    private String address;
    private String roadAddress;
    private String link;
    private String mapx;
    private String mapy;
    private String description;
}
