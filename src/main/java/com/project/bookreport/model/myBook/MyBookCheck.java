package com.project.bookreport.model.myBook;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MyBookCheck {
  private boolean check;
  private Long myBookId;
}
