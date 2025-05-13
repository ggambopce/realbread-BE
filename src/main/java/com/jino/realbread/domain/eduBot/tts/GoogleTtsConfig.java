package com.jino.realbread.domain.eduBot.tts;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.texttospeech.v1.TextToSpeechSettings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
public class GoogleTtsConfig {

    @Value("${spring.cloud.gcp.credentials.location}")
    private Resource gcsCredentials;

    @Bean
    public TextToSpeechSettings textToSpeechSettings() {
        try {
            return TextToSpeechSettings.newBuilder()
                    .setCredentialsProvider(() ->
                            GoogleCredentials.fromStream(gcsCredentials.getInputStream()))
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("TTS 설정 실패", e);
        }
    }
}