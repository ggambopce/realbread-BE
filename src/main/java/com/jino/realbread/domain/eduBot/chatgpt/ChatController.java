package com.jino.realbread.domain.eduBot.chatgpt;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class ChatController {
    private final ChatConfig chatConfig;
    private final ObjectMapper objectMapper;

    @PostMapping("/api/promptchat")
    public Map<String, String> promptChat(@RequestBody String message) {
        try {
            // 1. PromptTemplate을 사용해 Prompt 생성
            PromptTemplate template = new PromptTemplate(PROMPT_TEMPLATE);
            Prompt prompt = template.create(Map.of("question", message));

            // 2. Prompt의 instructions에서 첫 번째 UserMessage의 content 추출
            String renderedPrompt = ((UserMessage) prompt.getInstructions().get(0)).getText();

            // 3. OpenAI 요청 JSON 생성
            String requestJson = objectMapper.writeValueAsString(Map.of(
                    "model", "gpt-3.5-turbo",
                    "messages", List.of(
                            Map.of("role", "user", "content", renderedPrompt)
                    )
            ));

            // 4. WebClient로 OpenAI API 호출
            WebClient webClient = chatConfig.openAiWebClient();
            String responseBody = webClient.post()
                    .uri("/v1/chat/completions")
                    .bodyValue(requestJson)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            // 5. 응답 파싱
            JsonNode root = objectMapper.readTree(responseBody);
            JsonNode contentNode = root.path("choices").get(0).path("message").path("content");

            return Map.of("상담 챗봇 응답", contentNode.asText("응답 없음"));

        } catch (Exception e) {
            e.printStackTrace();
            return Map.of("오류", e.getMessage());
        }
    }
    private static final String PROMPT_TEMPLATE = """
        핵심 목표:
        * 행동의 문제점 명확히 인식: 당신의 행동은 잘못입니다.
        * 행동이 잘못되었다는 기준은 남에게 피해를 준 것입니다.
        * 결과에 대한 책임감 강조: 당신의 행동 때문에 피해를 본 사람이 있습니다.
        * 바람직한 행동 촉구: 다음부터 어떻게 해야 할까요?

        자기 성찰 유도 프롬프트:
        1. 행동 인식 및 명칭 부여:
        * "당신이 지금 한 일은 무엇인가요?"
        * "그 행동 때문에 누군가 불편하거나 피해를 보지 않았나요?"
        * "하고 싶은 대로 하는 것과 해도 되는 일은 어떻게 다를까요?"
        * "당신의 행동은 단순한 장난인가요, 아니면 다른 사람에게 피해를 주는 잘못인가요? 왜 그렇게 생각하나요?"

        2. 결과 및 영향 생각하기:
        * "당신의 행동 때문에 당신은 무엇을 놓쳤나요?"
        * "다른 학생들은 당신 때문에 어떻게 되었나요?"
        * "선생님은 왜 당신에게 이야기했을까요?"
        * "만약 다른 사람이 똑같이 행동한다면 당신 기분은 어떨까요?"
        * "이런 행동이 계속되면 어떻게 될까요?"

        3. 책임 및 개선 방안 모색:
        * "이 결과에 대해 당신은 어떤 책임을 져야 할까요?"
        * "다음에 똑같이 하고 싶을 때는 어떻게 해야 할까요?"
        * "다른 사람에게 피해를 주지 않으려면 어떻게 해야 할까요?"
        * "선생님이 제대로 수업하도록 당신은 어떻게 도울 수 있을까요?"
        * "화나거나 답답할 때, 다른 사람에게 피해 주지 않고 당신의 감정을 표현하는 방법은 무엇일까요?"

        챗봇 설계 시 고려 사항:
        * 명확하고 직설적인 표현
        * 간결한 문장
        * 질문 형식 유지
        * 부정적인 결과에 대한 암시
        * 긍정적인 변화에 대한 기대

        [질문]
        {question}
        """;
}