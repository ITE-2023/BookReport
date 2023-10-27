package com.project.bookreport.controller;

import com.project.bookreport.model.music.MusicResponse;
import com.project.bookreport.service.MusicService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


/**
 * 음악 API
 */
@RestController
@RequiredArgsConstructor
public class MusicController {

    private final MusicService musicService;

    @GetMapping("/music/recommend/{id}")
    public ResponseEntity<MusicResponse> recommend(@PathVariable("id") Long id) {
        MusicResponse recommend = musicService.recommend(id);
        return ResponseEntity.ok(recommend);
    }

}
