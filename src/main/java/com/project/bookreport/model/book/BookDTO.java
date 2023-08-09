package com.project.bookreport.model.book;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class BookDTO {
    private Long id;
    private String bookName;
    private String author;
    private String publisher;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
}
