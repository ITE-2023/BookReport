package com.project.bookreport.service;

import static com.project.bookreport.exception.ErrorCode.MUSIC_NOT_RECOMMEND;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.bookreport.domain.status.EmotionType;
import com.project.bookreport.exception.custom_exceptions.MusicException;
import com.project.bookreport.model.music.MusicRequest;
import com.project.bookreport.model.music.MusicResponse;
import com.project.bookreport.model.report.ReportDTO;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@RequiredArgsConstructor
public class MusicService {
    private final ReportService reportService;

    public MusicResponse recommend(Long id) {

        ReportDTO report = reportService.getReportById(id);
        EmotionType emotionType = report.getEmotionType();
        if (emotionType == null) {
            throw new MusicException(MUSIC_NOT_RECOMMEND);
        }

        MusicRequest request = MusicRequest.builder().emotion(emotionType.getMsg()).build();
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonContent;
        try {
            jsonContent = objectMapper.writeValueAsString(request);
        } catch (JsonProcessingException e) {
            throw new MusicException(MUSIC_NOT_RECOMMEND);
        }

        RestTemplate restTemplate = new RestTemplate();
        URI targetUrl = UriComponentsBuilder
                .fromUriString("http://localhost:5000/music")
                .build()
                .encode(StandardCharsets.UTF_8)
                .toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
        HttpEntity<Object> httpEntity = new HttpEntity<>(jsonContent, headers);

        try {
            return restTemplate.exchange(targetUrl, HttpMethod.POST, httpEntity, MusicResponse.class)
                    .getBody();
        } catch (Exception e) {
            throw new MusicException(MUSIC_NOT_RECOMMEND);
        }
    }
}
