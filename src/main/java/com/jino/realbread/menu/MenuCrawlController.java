package com.jino.realbread.menu;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MenuCrawlController {

    private final NaverMenuCrawler naverMenuCrawler;

    @GetMapping("/menus")
    public List<MenuDto> getMenus(@RequestParam String keyword) {
        return naverMenuCrawler.crawl(keyword);
    }
}
