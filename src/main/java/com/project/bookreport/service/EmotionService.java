package com.project.bookreport.service;

import com.project.bookreport.domain.Emotion;
import com.project.bookreport.repository.EmotionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmotionService {

    private final EmotionRepository emotionRepository;

    public Emotion create() {
        Emotion emotion = new Emotion();
        return emotionRepository.save(emotion);
    }
}
