package com.project.bookreport.domain;

import com.project.bookreport.domain.base.BaseEntity;
import com.project.bookreport.domain.status.EmotionType;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@Entity
@SuperBuilder
public class Emotion extends BaseEntity {
    private Integer happy;
    private Integer anger;
    private Integer sad;
    private Integer surprised;
    private Integer scary;

    public Emotion() {
        this.happy = 0;
        this.anger = 0;
        this.sad = 0;
        this.surprised = 0;
        this.scary = 0;
    }

    public void updateCount(EmotionType emotionType) {
        if (emotionType == EmotionType.HAPPY) {
            this.happy++;
        } else if (emotionType == EmotionType.ANGER) {
            this.anger++;
        } else if (emotionType == EmotionType.SAD) {
            this.sad++;
        } else if (emotionType == EmotionType.SURPRISED) {
            this.surprised++;
        } else if (emotionType == EmotionType.SCARY) {
            this.scary++;
        }
    }
}
