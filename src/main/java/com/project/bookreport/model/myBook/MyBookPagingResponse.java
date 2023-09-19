package com.project.bookreport.model.myBook;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MyBookPagingResponse {

  private int totalPage;
  private List<MyBookResponse> myBooks;
}
