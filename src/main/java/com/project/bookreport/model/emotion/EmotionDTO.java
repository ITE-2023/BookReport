package com.project.bookreport.model.emotion;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class EmotionDTO {
    private Long id;
    private Integer happy;
    private Integer anger;
    private Integer sad;
    private Integer surprised;
    private Integer scary;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
}
