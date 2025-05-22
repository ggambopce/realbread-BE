package com.jino.realbread.domain.eduBot.dto.response;

import com.jino.realbread.domain.eduBot.entity.Emotion;

import lombok.Getter;

@Getter
public class ChatEmotionAudioResponse {
    private byte[] audio;
    private Emotion emotion;

    public ChatEmotionAudioResponse(byte[] audio, Emotion emotion) {
        this.audio = audio;
        this.emotion = emotion;
    }
}
