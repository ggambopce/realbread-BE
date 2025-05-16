package com.jino.realbread.domain.eduBot.service;

import org.springframework.web.multipart.MultipartFile;

import com.jino.realbread.domain.eduBot.dto.ChatEmotionAudioResponse;

public interface CounselService {

    ChatEmotionAudioResponse processCounsel(MultipartFile audioFile);
}
