package com.jino.realbread.domain.eduBot.chatgpt;

import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.vertexai.gemini.VertexAiGeminiChatModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class ChatController {
    private final OpenAiChatModel openAiChatModel;
    //private final VertexAiGeminiChatModel vertexAiGeminiChatModel;


    public ChatController(OpenAiChatModel openAiChatModel, VertexAiGeminiChatModel vertexAiGeminiChatModel) {
        this.openAiChatModel = openAiChatModel;
        //this.vertexAiGeminiChatModel = vertexAiGeminiChatModel;
    }

    @PostMapping("api/chat")
    public Map<String, String> chat(@RequestBody String message) {
        Map<String, String> responses = new HashMap<>();

        String openAiResponse = openAiChatModel.call(message);
        responses.put("openai(chatGPT) 응답", openAiResponse);


        //String vertexAiGeminiResponse = vertexAiGeminiChatModel.call(message);
        //responses.put("vertexai(gemini) 응답", vertexAiGeminiResponse);

        return responses;
    }

    @PostMapping("api/promptchat")
    public Map<String, String> promptChat(@RequestBody String message) {
        Map<String, String> responses = new HashMap<>();

        // 챗봇 성향 프롬프트: 아이들을 위한 상담자 역할
        PromptTemplate promptTemplate = new PromptTemplate("""
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
                           
                           챗봇 설계 시 고려 사항 (강조):
                            * 명확하고 직설적인 표현: 에둘러 말하지 않고 핵심을 바로 전달합니다.
                            * 간결한 문장: 이해하기 쉽도록 짧고 명확한 문장을 사용합니다.
                            * 질문 형식 유지: 스스로 생각하도록 유도하는 질문 형태를 유지합니다.
                            * 부정적인 결과에 대한 명확한 암시: 행동의 잘못됨과 그로 인한 불이익을 간접적으로 시사합니다.
                            * 긍정적인 변화에 대한 기대: 앞으로의 바람직한 행동 변화를 촉구합니다.
                           
                           이 프롬프트는 학생들에게 더욱 직접적으로 상황을 인식시키고, 자신의 행동이 명백한 잘못임을 깨닫게 하는 데 중점을 두어야 합니다.

                [질문]
                {question}
                """);

        Prompt prompt = promptTemplate.create(Map.of("question", message));
        ChatResponse chatResponse = openAiChatModel.call(prompt);

        String content = chatResponse.getResults().get(0).getOutput().getText();
        // 이건 버전에 따라 다름

        responses.put("상담 챗봇 응답", content);

        return responses;
    }

}
