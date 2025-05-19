package com.jino.realbread.menu.crawler;

import com.jino.realbread.menu.MenuDto;
import com.jino.realbread.menu.service.implement.MenuServiceImplement;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/crawler")
public class MenuCrawlController {

    private final CrawlerService crawlerService;

    @GetMapping("/menus")
    public ResponseEntity<String> crawlAllMenus() {
        crawlerService.crawlAllMenus();
        return ResponseEntity.ok("크롤링 완료");
    }

}
