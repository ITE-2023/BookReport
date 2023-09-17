package com.project.bookreport.model.book;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BookDetailDTO {

    private String title;
    private String image;
    private String author;
    private String isbn;
    private String publisher;
    private String description;
}
