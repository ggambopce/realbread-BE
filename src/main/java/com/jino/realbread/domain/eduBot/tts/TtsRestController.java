package com.jino.realbread.domain.eduBot.tts;

import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tts")
public class TtsRestController {

    private final TextToSpeechService ttsService;

    @PostMapping("/speak")
    public ResponseEntity<byte[]> getSpeechFromText(@RequestBody String text) throws IOException {
        byte[] audioBytes = ttsService.synthesize(text);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("audio/mpeg")); // 명확하게 audio 형식으로 지정
        headers.setContentLength(audioBytes.length);
        headers.setContentDisposition(ContentDisposition.attachment()
                .filename("speech.mp3")
                .build());

        return new ResponseEntity<>(audioBytes, headers, HttpStatus.OK);
    }
}
