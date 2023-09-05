package com.project.bookreport.model.myBook;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MyBookDTO {
  private Long id;
  private String myBookStatus;
  private Integer rate;
  private LocalDateTime startDate;
  private LocalDateTime endDate;
  private LocalDateTime createDate;
  private LocalDateTime updateDate;
}
