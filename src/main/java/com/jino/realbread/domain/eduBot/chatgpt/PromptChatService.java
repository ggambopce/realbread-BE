package com.jino.realbread.domain.eduBot.chatgpt;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jino.realbread.domain.eduBot.dto.ChatEmotionResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PromptChatService {

    private final WebClient openAiWebClient;
    private final ObjectMapper objectMapper;

    private static final String MODEL = "gpt-3.5-turbo";

    public ChatEmotionResponse counselTextResponse(String message) {
        if (message == null || message.isBlank()) {
            throw new IllegalArgumentException("상담 질문이 비어 있습니다.");
        }

        try {
            // 1. 프롬프트 생성
            PromptTemplate promptTemplate = new PromptTemplate(PROMPT_TEMPLATE);
            Prompt prompt = promptTemplate.create(Map.of("question", message));

            // 2. Prompt의 instructions에서 첫 번째 UserMessage의 content 추출
            String renderedPrompt = ((UserMessage) prompt.getInstructions().get(0)).getText();

            // 3. OpenAI 요청 JSON 생성
            String requestJson = objectMapper.writeValueAsString(Map.of(
                    "model", MODEL,
                    "messages", List.of(
                            Map.of("role", "user", "content", renderedPrompt))));

            // 4. WebClient로 OpenAI API 호출
            String responseBody = openAiWebClient.post()
                    .uri("/v1/chat/completions")
                    .bodyValue(requestJson)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            // 5. 응답 파싱
            if (responseBody == null || responseBody.isBlank()) {
                throw new RuntimeException("응답 본문이 비어 있습니다.");
            }

            JsonNode root = objectMapper.readTree(responseBody);
            JsonNode contentNode = root.path("choices").get(0).path("message").path("content");

            // contentNode는 JSON String을 담고 있음. 다시 파싱해야 함.
            String contentJson = contentNode.asText();
            return objectMapper.readValue(contentJson, ChatEmotionResponse.class);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("OpenAI 호출 실패: " + e.getMessage());
        }
    }

    private static final String PROMPT_TEMPLATE = """

            챗봇 설계 시 고려 사항:
            * 명확하고 정확한 정보 전달
            * 따뜻한 대화체 문장
            * 대전 자랑을 꼭 포함
            * 빵이 많고 노잼도시가 아님을 강조
            * 긍정과 유머러스한 상담

            응답 형식 (JSON):
            다음 형식으로 대답해 주세요. 응답만 출력하세요. 감정(emotion)은 아래에서 선택하세요.

            "response": 실제 상담 메시지를 이곳에 작성(문장 형태),
            "emotion": 반드시 아래 목록중 하나의 감정과 정확하게 일치해야 함

            Emotion Enum 목록:
            거부, 고민중, 그건아니지화남, 긍적긍적미안, 기대만발, 기본스마일, 나센스있는데, 나짜증, 나황당, 난감한표정, 너무미안, 너무좋아사랑, 놀래키기, 누군가를좋아함, 달콤하고맛있어, 덜덜무서움, 따봉좋아, 땀삐질당황, 똑똑이, 미안, 바램, 뿌뿌신난다, 삐짐슬픔, 사랑고백하트, 생일축하, 손가락비난, 수줍은안녕, 수줍은웃음, 수줍은하트, 수줍은헤벌레사랑, 싫어, 심각한고민, 싸우고싶은화남, 싸우자, 안녕, 오궁금, 요리시작, 요리중, 우거절비난, 우쭐나최고, 위험해멈춰, 유혹, 으악걱정돼, 자아도취, 졸림, 좋은아이디어, 집중해, 찾는중, 키득키득, 한잔해, 헤벌레사랑에빠짐, 화나는데들어봄, 훌쩍슬픔

            질문: $question$
                        """;
}