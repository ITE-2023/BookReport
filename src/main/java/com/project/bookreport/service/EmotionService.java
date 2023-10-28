package com.project.bookreport.service;

import static com.project.bookreport.exception.ErrorCode.EMOTION_NOT_FOUND;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.bookreport.domain.Book;
import com.project.bookreport.domain.Emotion;
import com.project.bookreport.domain.status.EmotionType;
import com.project.bookreport.exception.custom_exceptions.EmotionException;
import com.project.bookreport.model.emotion.EmotionRequest;
import com.project.bookreport.model.emotion.EmotionResponse;
import com.project.bookreport.repository.EmotionRepository;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmotionService {

    private final EmotionRepository emotionRepository;

    public Emotion create() {
        Emotion emotion = new Emotion();
        return emotionRepository.save(emotion);
    }

    public EmotionType update(String content, Book book) {
        EmotionResponse response = analysisEmotion(content);
        if (response == null) {
            return null;
        }
        String responseEmotion = response.getEmotion();
        Emotion emotion = book.getEmotion();
        for (EmotionType emotionType : EmotionType.values()) {
            if (emotionType.getMsg().equals(responseEmotion)) {
                emotion.updateCount(emotionType);
                return emotionType;
            }
        }
        return null;
    }

    private EmotionResponse analysisEmotion(String content) {
        EmotionRequest request = EmotionRequest.builder().content(content).build();
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonContent;
        try {
            jsonContent = objectMapper.writeValueAsString(request);
        } catch (JsonProcessingException e) {
            throw new EmotionException(EMOTION_NOT_FOUND);
        }

        RestTemplate restTemplate = new RestTemplate();
        URI targetUrl = UriComponentsBuilder
                .fromUriString("http://localhost:5000/emotion")
                .build()
                .encode(StandardCharsets.UTF_8)
                .toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
        HttpEntity<Object> httpEntity = new HttpEntity<>(jsonContent, headers);

        try {
            return restTemplate.exchange(targetUrl, HttpMethod.POST, httpEntity, EmotionResponse.class)
                    .getBody();
        } catch (Exception e) {
            throw new EmotionException(EMOTION_NOT_FOUND);
        }
    }
}
