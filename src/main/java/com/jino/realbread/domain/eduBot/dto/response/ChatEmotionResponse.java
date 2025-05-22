package com.jino.realbread.domain.eduBot.dto.response;

import com.jino.realbread.domain.eduBot.entity.Emotion;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ChatEmotionResponse {
    private String response;
    private Emotion emotion;
}
