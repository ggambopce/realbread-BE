package com.jino.realbread.domain.eduBot.stt;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/stt")
public class SttRestController {

    private final SpeechToTextService sttService;
    /**
     * 오디오 파일을 받아서 텍스트로 변환하여 반환
     */
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> handleAudioMessage(@RequestParam("file") MultipartFile audioFile) throws IOException {

        int frequency = 44100; // 고정 샘플레이트 설정
        String transcribe = sttService.transcribe(audioFile, frequency);
        return ResponseEntity.ok(transcribe);
    }
}
