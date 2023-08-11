package com.project.bookreport.model.report;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ReportDTO {
  private Long id;
  private String title;
  private String content;
  private Long memberId;
  private Long bookId;
  private LocalDateTime createDate;
  private LocalDateTime updateDate;
}
