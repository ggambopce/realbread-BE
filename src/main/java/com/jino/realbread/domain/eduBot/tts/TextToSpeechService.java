package com.jino.realbread.domain.eduBot.tts;

import com.google.cloud.texttospeech.v1.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class TextToSpeechService {

    private final TextToSpeechSettings textToSpeechSettings;

    public byte[] synthesize(String text) throws IOException {
        // 설정 기반으로 클라이언트 생성 (인증 포함)
        try (TextToSpeechClient textToSpeechClient = TextToSpeechClient.create(textToSpeechSettings)) {

            // 입력 텍스트 설정
            SynthesisInput input = SynthesisInput.newBuilder()
                    .setText(text)
                    .build();

            // 목소리 설정 (언어, 성별 등)
            VoiceSelectionParams voice = VoiceSelectionParams.newBuilder()
                    .setLanguageCode("ko-KR")
                    .setName("ko-KR-Wavenet-B") //ko-KR-Wavenet-B,ko-KR-Neural2-B
                    .build();

            // 오디오 출력 설정 (MP3 or LINEAR16 등)
            AudioConfig audioConfig = AudioConfig.newBuilder()
                    .setAudioEncoding(AudioEncoding.MP3)
                    .build();

            // 텍스트 → 음성 요청
            SynthesizeSpeechResponse response = textToSpeechClient.synthesizeSpeech(input, voice, audioConfig);

            // 결과 오디오 바이트 반환
            return response.getAudioContent().toByteArray();
        }
    }

    // 파일로 저장하고 싶을 경우
    public void saveAsFile(String text, String filePath) throws IOException {
        byte[] audioData = synthesize(text);
        try (FileOutputStream out = new FileOutputStream(filePath)) {
            out.write(audioData);
        }
    }
}
