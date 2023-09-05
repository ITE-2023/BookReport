package com.project.bookreport.model.myBook;

import com.project.bookreport.model.book.BookDTO;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MyBookResponse{
  private BookDTO bookDTO;
  private MyBookDTO myBookDTO;
}
