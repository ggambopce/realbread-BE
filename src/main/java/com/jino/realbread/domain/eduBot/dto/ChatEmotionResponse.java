package com.jino.realbread.domain.eduBot.dto;

import com.jino.realbread.domain.eduBot.entity.Emotion;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatEmotionResponse {
    private String response;
    private Emotion emotion;
}
