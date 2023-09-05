package com.project.bookreport.model.myBook;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class MyBookRequest {
  @NotBlank(message = "책 상태를 입력해주세요")
  private String myBookStatus;
  private Integer rate;
  private LocalDateTime startDate;
  private LocalDateTime endDate;
}
