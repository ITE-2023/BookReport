package com.project.bookreport.domain;

import com.project.bookreport.domain.base.BaseEntity;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
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

    @OneToMany(mappedBy = "book")
    private List<Report> reportList = new ArrayList<>();
}
