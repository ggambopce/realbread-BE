package com.jino.realbread.menu;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.time.Duration;
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

        JavascriptExecutor js = (JavascriptExecutor) driver;

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        try {
            // 검색어로 지도 검색 페이지 열기
            String url = "https://map.naver.com/v5/search/" + URLEncoder.encode(keyword, "UTF-8");
            driver.get(url);
            Thread.sleep(3000); // 초기 로딩 대기

            // searchIframe 감지될 때까지 대기
            while (!(Boolean) js.executeScript("return document.querySelector('iframe#searchIframe') !== null;")) {
                Thread.sleep(500);
            }
            driver.switchTo().frame("searchIframe");

            // 가게 이름 요소들 가져오기 (텍스트 기반 안전한 셀렉터)
            List<WebElement> shopLinks = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                    By.cssSelector("div.place_bluelink>.TYaxT, div.place_bluelink>span.YwYLL")
            ));

            if (shopLinks.isEmpty()) {
                System.out.println("가게 항목 없음");
                return result;
            }

            // 첫 번째 가게 클릭
            WebElement listContainer = driver.findElement(By.xpath("//*[@id=\"_pcmap_list_scroll_container\"]/ul"));
            List<WebElement> links = listContainer.findElements(By.tagName("a"));

            if (links.isEmpty()) {
                System.out.println("가게 리스트 없음");
                return null;
            }

            // 첫 번째 가게 클릭 시도 (JavaScript로 강제)
            WebElement shop = shopLinks.get(0);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", shop);

            // 클릭 이후 URL 변화 기다리기
            while (!driver.getCurrentUrl().contains("/place/")) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", shop);
                Thread.sleep(10000); // 재시도 대기
            }

            // 상세페이지 진입 성공 이후
            String currentUrl = driver.getCurrentUrl();

            // placePath 파라미터를 메뉴로 교체
            if (currentUrl.contains("placePath=")) {
                String menuUrl = currentUrl.replaceAll("placePath=[^&]*", "placePath=/menu");
                driver.get(menuUrl);
                Thread.sleep(1500); // 메뉴탭 로딩 대기
            } else {
                System.out.println("상세 페이지 URL에 placePath가 없어 메뉴탭 접근 불가");
                return result;
            }

            // entryIframe 전환까지 안전 처리
            while (true) {
                try {
                    driver.switchTo().defaultContent(); // 원래 프레임으로
                    wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(
                            By.cssSelector("iframe#entryIframe")));
                    break; // 전환 성공하면 탈출
                } catch (NoSuchFrameException e) {
                    Thread.sleep(500); // 재시도 대기
                }
            }


            // 5. 메뉴 요소 탐색 (두 가지 클래스 중 하나)
            List<WebElement> menus = driver.findElements(By.className("_3ak_I")); // 배달의민족 제공
            if (menus.isEmpty()) {
                menus = driver.findElements(By.className("V1UmJ")); // 가게 제공 메뉴
            }

            if (menus.isEmpty()) {
                System.out.println("메뉴가 없습니다");
                return null;
            }

            // 5. 메뉴 항목들 크롤링
            List<WebElement> menuElements = driver.findElements(By.cssSelector(".menu_box .menu_item"));
            if (menuElements.isEmpty()) {
                System.out.println("메뉴 항목 없음");
                return result;
            }

            for (WebElement menu : menuElements) {
                try {
                    String name = menu.findElement(By.cssSelector(".menu_name")).getText();
                    String price = menu.findElement(By.cssSelector(".menu_price")).getText();
                    String imageUrl = "";
                    try {
                        imageUrl = menu.findElement(By.cssSelector("img")).getAttribute("src");
                    } catch (Exception ignore) {}
                    result.add(new MenuDto(name, price, imageUrl));
                } catch (Exception e) {
                    // 개별 메뉴 요소에 문제 있을 경우 건너뜀
                    continue;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }

        return result;
    }
}
