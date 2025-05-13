package com.jino.realbread.domain.eduBot.service;

import org.springframework.web.multipart.MultipartFile;

public interface CounselService {

    byte[] processCounsel(MultipartFile audioFile);
}
