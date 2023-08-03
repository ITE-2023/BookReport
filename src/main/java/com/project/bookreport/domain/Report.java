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
    @ManyToOne(fetch = FetchType.LAZY)//한번에 값을 다 가져옴--> Member select문도 가져옴
    private Member member;

    private Book book;


}
