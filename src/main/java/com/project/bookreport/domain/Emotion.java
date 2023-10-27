package com.project.bookreport.domain;

import com.project.bookreport.domain.base.BaseEntity;
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
}
