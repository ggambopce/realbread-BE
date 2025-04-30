package com.jino.realbread.menu;

import com.jino.realbread.menu.service.implement.MenuServiceImplement;
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

    private final MenuServiceImplement menuService;

    @GetMapping("/menus")
    public List<MenuDto> getMenus(@RequestParam String keyword) {
        return naverMenuCrawler.crawl(keyword);
    }

    @GetMapping("/menus/save")
    public String crawlAndSaveMenus(@RequestParam String keyword) {
        List<MenuDto> menuList = naverMenuCrawler.crawl(keyword);
        menuService.saveAllMenus(menuList);
        return "저장 완료: " + menuList.size() + "건";
    }
}
