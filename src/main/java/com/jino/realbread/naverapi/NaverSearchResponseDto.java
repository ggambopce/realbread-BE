package com.jino.realbread.naverapi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class NaverSearchResponseDto {
    private String lastBuildDate;
    private int total;
    private int start;
    private int display;
    private List<NaverSearchBakery> items;
}
