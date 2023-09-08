package com.project.bookreport.model.myBook;

import com.project.bookreport.model.book.BookRequest;
import lombok.Getter;

@Getter
public class MyBookVO {
  private BookRequest bookRequest;
  private MyBookRequest myBookRequest;
}
