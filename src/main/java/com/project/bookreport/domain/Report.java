package com.project.bookreport.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.bookreport.domain.base.BaseEntity;
import com.project.bookreport.domain.status.EmotionType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@Entity
@SuperBuilder
@NoArgsConstructor
public class Report extends BaseEntity {
    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private  String content;

    @Column(nullable = false)
    private String username;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private Book book;

    @Enumerated(EnumType.STRING)
    private EmotionType emotionType;
}
