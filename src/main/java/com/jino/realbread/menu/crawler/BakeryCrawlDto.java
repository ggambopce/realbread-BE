package com.jino.realbread.menu.crawler;

import lombok.Getter;

@Getter
public class BakeryCrawlDto {
    private Long bakeryId;
    private String title;
    private String address;

    public BakeryCrawlDto(Long bakeryId, String title, String address) {
        this.bakeryId = bakeryId;
        this.title = title;
        this.address = address;
    }
}
