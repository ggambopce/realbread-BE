package com.jino.realbread.menu.crawler;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jino.realbread.domain.bakery.repository.BakeryRepository;
import com.jino.realbread.menu.Menu;
import com.jino.realbread.menu.MenuDto;
import com.jino.realbread.menu.repository.MenuRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CrawlerServiceImplement implements CrawlerService {

    private final NaverMenuCrawler naverMenuCrawler;
    private final BakeryRepository bakeryRepository;
    private final MenuRepository menuRepository;

    @Override
    @Transactional
    public void crawlAllMenus() {
        List<BakeryCrawlDto> bakeryList = bakeryRepository.findAllForCrawling(); // name, address 포함

        int crawlCount = 0;
        final int MAX_CRAWL = 3;

        for (BakeryCrawlDto bakery : bakeryList) {
            if (crawlCount >= MAX_CRAWL) {
                System.out.println("최대 크롤링 개수 도달: 종료");
                break;
            }

            crawlCount++; // 저장 성공 시만 증가

            // 이미 메뉴가 있는 빵집은 스킵
            boolean exists = menuRepository.existsByBakeryId(bakery.getBakeryId());
            if (exists) {
                System.out.println("이미 메뉴 있음 → 스킵: " + bakery.getTitle());
                continue;
            }

            try {
                System.out.println("크롤링 시작: " + bakery.getTitle());
                List<MenuDto> menuList = naverMenuCrawler.crawl(bakery);

                if (menuList.isEmpty()) {
                    System.out.println("메뉴 없음: " + bakery.getTitle());
                    continue;
                }

                // MenuDto → MenuEntity 변환 후 저장
                List<Menu> menuEntities = menuList.stream()
                        .map(dto -> Menu.builder()
                                .menuName(dto.getMenuName())
                                .price(dto.getPrice())
                                .description(dto.getDescription())
                                .imageUrl(dto.getImageUrl())
                                .bakeryId(bakery.getBakeryId())
                                .build())
                        .toList();

                menuRepository.saveAll(menuEntities);

                System.out.println("저장 완료: " + bakery.getTitle() + " (" + menuEntities.size() + "개)");

            } catch (Exception e) {
                System.out.println("크롤링 실패: " + bakery.getTitle());
                e.printStackTrace();
            }
        }
    }
}