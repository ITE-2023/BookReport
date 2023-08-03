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
public class Book extends BaseEntity {
    @Column(length = 20)
    private String bookName;
    private String author;
    private String publisher;
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY)
    private Report report;

}
