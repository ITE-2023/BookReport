package com.project.bookreport.controller;

import com.project.bookreport.model.emotion.EmotionDTO;
import com.project.bookreport.service.EmotionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EmotionController {

    private final EmotionService emotionService;

    @GetMapping("/emotion/{isbn}")
    public ResponseEntity<EmotionDTO> getEmotionByBook(@PathVariable("isbn") String isbn) {
        EmotionDTO emotionDTO = emotionService.getEmotionByBook(isbn);
        return ResponseEntity.ok(emotionDTO);
    }
}
