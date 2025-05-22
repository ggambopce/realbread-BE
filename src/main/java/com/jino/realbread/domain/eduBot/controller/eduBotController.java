package com.jino.realbread.domain.eduBot.controller;

import com.jino.realbread.domain.eduBot.chatgpt.PromptChatService;
import com.jino.realbread.domain.eduBot.dto.request.ChatTextRequestDto;
import com.jino.realbread.domain.eduBot.dto.response.ChatEmotionAudioResponse;
import com.jino.realbread.domain.eduBot.dto.response.ChatEmotionResponse;
import com.jino.realbread.domain.eduBot.service.CounselService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/edu-bot")
public class eduBotController {

    private final CounselService counselService;
    private final PromptChatService promptChatService;

    @PostMapping(value = "/counsel", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> processCounsel(@RequestParam("audioFile") MultipartFile audioFile) {

        if (audioFile == null)
            throw new RuntimeException("파일이 null입니다");

        ChatEmotionAudioResponse response = counselService.processCounsel(audioFile);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentLength(response.getAudio().length);
        headers.set("Content-Disposition", "attachment; filename=\"response.mp3\"");
        headers.set("Emotion", response.getEmotion().name());

        return new ResponseEntity<>(response.getAudio(), headers, HttpStatus.OK);
    }

    @PostMapping("/counsel/text")
    public ResponseEntity<ChatEmotionResponse> chatWithBakeryBot(@RequestBody ChatTextRequestDto request) {
        String message = request.getMessage();
        ChatEmotionResponse response = promptChatService.counselTextResponse(message);
        return ResponseEntity.ok(response);
    }

}
