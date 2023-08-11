package com.project.bookreport.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.bookreport.domain.base.BaseEntity;
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
    private String title;

    @Column(columnDefinition = "TEXT")
    private  String content;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private Book book;
}
