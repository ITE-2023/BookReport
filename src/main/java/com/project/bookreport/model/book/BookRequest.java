package com.project.bookreport.model.book;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class BookRequest {

    @NotBlank(message = "책 제목을 적어주세요.")
    private String bookName;
    @NotBlank(message = "작가의 이름을 적어주세요.")
    private String author;
    @NotBlank(message = "책의 출판사를 적어주세요.")
    private String publisher;
    @NotBlank(message = "책의 isbn을 적어주세요.")
    private String isbn;
}
