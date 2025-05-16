package com.jino.realbread.domain.eduBot.service.implement;

import com.jino.realbread.domain.eduBot.chatgpt.PromptChatService;
import com.jino.realbread.domain.eduBot.dto.ChatEmotionAudioResponse;
import com.jino.realbread.domain.eduBot.dto.ChatEmotionResponse;
import com.jino.realbread.domain.eduBot.entity.Emotion;
import com.jino.realbread.domain.eduBot.service.CounselService;
import com.jino.realbread.domain.eduBot.stt.SpeechToTextService;
import com.jino.realbread.domain.eduBot.tts.TextToSpeechService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class CounselServiceImplement implements CounselService {
    /*
     * 컨트롤러에서 audioChunks를 Blob으로 합쳐 FormData에 첨부
     * 여기에서 stt를 주입하여 텍스트변경
     * 변경된 텍스트 chatgpt 주입하여 상담
     * 상담 후 tts 주입하여 음성파일로 변경
     * 응답은 음성파일형식으로 리액트에 전달
     */
    private final SpeechToTextService speechToTextService;
    private final PromptChatService promptChatService;
    private final TextToSpeechService textToSpeechService;

    public ChatEmotionAudioResponse processCounsel(MultipartFile audioFile) {
        try {
            // 1. STT: 음성 → 텍스트
            String transcribedText = speechToTextService.transcribe(audioFile, 44100); // 필요 시 샘플레이트 조정

            // 2. GPT: 텍스트 → 상담 응답 텍스트
            ChatEmotionResponse chat = promptChatService.counselTextResponse(transcribedText);
            String message = chat.getResponse();
            Emotion emotion = chat.getEmotion();
            // 3. TTS: 응답 텍스트 → 음성 파일(byte[])
            byte[] audio = textToSpeechService.synthesize(message);

            // 4. 감정 + 오디오 패키징
            return new ChatEmotionAudioResponse(audio, emotion);

        } catch (Exception e) {
            throw new RuntimeException("상담 처리 중 오류 발생: " + e.getMessage(), e);
        }
    }
}
