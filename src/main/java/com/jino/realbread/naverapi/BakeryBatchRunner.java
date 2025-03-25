package com.jino.realbread.naverapi;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class BakeryBatchRunner {

    private final NaverSearchService naverSearchService;

    @PostConstruct // 또는 스케줄링용 @Scheduled(fixedRate = ...)
    public void run() {
        List<String> dongs = List.of(
                // 유성구
                "궁동", "구암동", "덕명동", "덕진동", "도룡동", "봉명동", "상대동", "성북동", "신동", "신성동",
                "어은동", "외삼동", "원내동", "원신흥동", "자운동", "장대동", "전민동", "계산동", "하기동", "학하동",

                // 서구
                "가장동", "갈마동", "관저동", "괴정동", "내동", "도마동", "둔산동", "만년동", "변동", "복수동",
                "용문동", "월평동", "정림동", "탄방동",

                // 중구
                "문화동", "부사동", "선화동", "석교동", "오류동", "유천동", "은행동", "중촌동", "태평동", "대흥동",
                "목동", "용두동",

                // 동구
                "가양동", "낭월동", "대동", "대성동", "삼성동", "성남동", "신안동", "신흥동", "인동", "자양동",
                "정동", "중동", "판암동", "홍도동", "효동",

                // 대덕구
                "법동", "비래동", "석봉동", "신대동", "신일동", "오정동", "와동", "읍내동", "중리동", "회덕동"
        );
        List<String> keywords = List.of("빵집", "베이커리");

        List<String> queries = dongs.stream()
                .flatMap(d -> keywords.stream().map(k -> "대전 " + d + " " + k))
                .collect(Collectors.toList());

        for (String query : queries) {
            System.out.println("▶ 검색: " + query);
            naverSearchService.searchAndSave(query);
        }
    }
}
