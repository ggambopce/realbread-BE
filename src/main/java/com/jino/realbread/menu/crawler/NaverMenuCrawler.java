package com.jino.realbread.menu.crawler;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;

import com.jino.realbread.menu.MenuDto;

import java.net.URLEncoder;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Component
public class NaverMenuCrawler {

    public List<MenuDto> crawl(BakeryCrawlDto dto) {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");

        WebDriver driver = new ChromeDriver(options);
        List<MenuDto> result = new ArrayList<>();

        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        try {
            // 1. 검색 페이지 접근
            String keyword = dto.getTitle() + " 대전";
            String url = "https://map.naver.com/v5/search/" + URLEncoder.encode(keyword, "UTF-8");
            driver.get(url);
            Thread.sleep(3000);

            // 2. searchIframe 진입
            while (!(Boolean) js.executeScript("return document.querySelector('iframe#searchIframe') !== null;")) {
                Thread.sleep(500);
            }
            driver.switchTo().frame("searchIframe");

            // 3. 첫 번째 검색 결과 중 "가게 이름" 요소 클릭
            WebElement titleElement = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.cssSelector("a.place_bluelink")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", titleElement);

            // 4. 상세 패널 iframe(entryIframe) 진입 시도
            int attempt = 0;
            while (true) {
                try {
                    driver.switchTo().defaultContent();
                    wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(
                            By.cssSelector("iframe#entryIframe")));
                    break;
                } catch (NoSuchFrameException e) {
                    Thread.sleep(500);
                    if (++attempt >= 3) {
                        System.out.println("entryIframe 전환 실패 (상세페이지)");
                        return result;
                    }
                }
            }

            // 5. 메뉴 탭 클릭
            try {
                WebElement menuTab = wait.until(ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//a[contains(@href, 'menu') or contains(text(), '메뉴')]")));
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", menuTab);
                Thread.sleep(1500);
            } catch (Exception e) {
                System.out.println("메뉴 탭 클릭 실패");
                return result;
            }

            // 6. 다시 entryIframe 진입 (메뉴 탭 전환 후)
            attempt = 0;
            while (true) {
                try {
                    driver.switchTo().defaultContent();
                    wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(
                            By.cssSelector("iframe#entryIframe")));
                    break;
                } catch (NoSuchFrameException e) {
                    Thread.sleep(500);
                    if (++attempt >= 3) {
                        System.out.println("entryIframe 전환 실패 (메뉴탭 클릭 후)");
                        return result;
                    }
                }
            }

            // 7. 메뉴 목록 크롤링
            List<WebElement> menuElements = driver.findElements(By.cssSelector("div.place_section_content ul li"));
            for (WebElement menu : menuElements) {
                try {
                    String name = getTextOrEmpty(menu, ".lPzHi");
                    String description = getTextOrEmpty(menu, ".kPogF");
                    String price = getTextOrEmpty(menu, ".GXS1X");
                    String imageUrl = "";

                    try {
                        WebElement img = menu.findElement(By.cssSelector(".place_thumb img"));
                        imageUrl = img.getAttribute("src");
                    } catch (NoSuchElementException ignore) {
                    }

                    if (!name.isEmpty()) {
                        result.add(new MenuDto(name, price, imageUrl, description));
                    }
                } catch (Exception ignore) {
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }

        return result;
    }

    private String getTextOrEmpty(WebElement element, String cssSelector) {
        try {
            return element.findElement(By.cssSelector(cssSelector)).getText();
        } catch (NoSuchElementException e) {
            return "";
        }
    }

    // 👇 이 메서드는 dto.getAddress()에서 "구 동"만 추출
    private String extractGuAndDong(String address) {
        String[] parts = address.split(" ");
        if (parts.length >= 3) {
            return parts[1] + " " + parts[2]; // 예: 유성구 관평동
        }
        return address; // 예외적으로 파싱 안 되면 전체 주소 사용
    }
}
