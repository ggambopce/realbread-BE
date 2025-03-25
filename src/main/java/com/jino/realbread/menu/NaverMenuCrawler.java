package com.jino.realbread.menu;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Component
public class NaverMenuCrawler {

    public List<MenuDto> crawl(String keyword) {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        WebDriver driver = new ChromeDriver(options);

        List<MenuDto> result = new ArrayList<>();

        try {
            String url = "https://map.naver.com/v5/search/" + URLEncoder.encode(keyword, "UTF-8");
            driver.get(url);
            Thread.sleep(4000); // 로딩 대기

            // 검색 결과 iframe으로 이동
            driver.switchTo().frame("searchIframe");

            // 첫 번째 결과 클릭
            WebElement first = driver.findElement(By.cssSelector(".place-result-list .place-result-item"));
            first.click();
            Thread.sleep(4000);

            // 업체 상세정보 iframe으로 전환
            driver.switchTo().defaultContent();
            driver.switchTo().frame("entryIframe");

            // 메뉴 정보 탐색
            List<WebElement> menus = driver.findElements(By.cssSelector(".menu_box .menu_item"));

            for (WebElement menu : menus) {
                String name = menu.findElement(By.cssSelector(".menu_name")).getText();
                String price = menu.findElement(By.cssSelector(".menu_price")).getText();
                String imageUrl = "";

                try {
                    imageUrl = menu.findElement(By.cssSelector("img")).getAttribute("src");
                } catch (Exception ignore) {}

                result.add(new MenuDto(name, price, imageUrl));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }

        return result;
    }
}
