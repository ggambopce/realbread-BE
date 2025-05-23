package com.jino.realbread.naverapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jino.realbread.domain.bakery.entity.Bakery;
import com.jino.realbread.domain.bakery.repository.BakeryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NaverSearchService {

    private final BakeryRepository bakeryRepository;

    @Value("${naver.client.id}")
    private String clientId;

    @Value("${naver.client.secret}")
    private String clientSecret;

    public void searchAndSave(String query) {
        try {
            String encoded = URLEncoder.encode(query, StandardCharsets.UTF_8);
            ObjectMapper objectMapper = new ObjectMapper();

            String apiURL = "https://openapi.naver.com/v1/search/local.json?query=" + encoded +"&display=5&start=1&sort=comment";

            HttpURLConnection con = (HttpURLConnection) new URL(apiURL).openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("X-Naver-Client-Id", clientId);
            con.setRequestProperty("X-Naver-Client-Secret", clientSecret);

            BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8));
            String response = br.lines().collect(Collectors.joining());
            br.close();


            NaverSearchResponseDto result = objectMapper.readValue(response, NaverSearchResponseDto.class);

            List<Bakery> bakeries = result.getItems().stream()
                    .map(item -> Bakery.builder()
                            .title(item.getTitle().replaceAll("<[^>]*>", "")) // HTML 태그 제거
                            .category(item.getCategory())
                            .address(item.getAddress())
                            .roadAddress(item.getRoadAddress())
                            .link(item.getLink())
                            .mapx(item.getMapx())
                            .mapy(item.getMapy())
                            .build())
                    .filter(bakery -> !bakeryRepository.existsByAddress(bakery.getAddress()))
                    .collect(Collectors.toList());
            System.out.println("DB에 저장할 개수: " + bakeries.size());
            System.out.println("총 검색 결과 수: " + result.getTotal());


            bakeryRepository.saveAll(bakeries);

        } catch (Exception e) {
            throw new RuntimeException("API 호출 실패", e);
        }
    }
}
