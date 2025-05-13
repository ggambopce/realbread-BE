package com.jino.realbread.domain.eduBot.controller;

import com.jino.realbread.domain.eduBot.service.CounselService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/edu-bot")
public class eduBotController {

    private final CounselService counselService;

    @PostMapping(value = "/counsel", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> processCounsel(@RequestParam("audioFile") MultipartFile audioFile) {

        System.out.println("audioFile: " + audioFile); // null인지 확인
        if (audioFile == null) throw new RuntimeException("파일이 null입니다");

        byte[] audioBytes = counselService.processCounsel(audioFile);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM); // 혹은 MediaType.valueOf("audio/webm")
        headers.setContentLength(audioBytes.length);
        headers.set("Content-Disposition", "attachment; filename=\"response.mp3\""); // 클라이언트 저장시 이름

        return new ResponseEntity<>(audioBytes, headers, HttpStatus.OK);
    }
}
