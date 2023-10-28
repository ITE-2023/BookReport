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
  private String username;
  private String emotion;
  private LocalDateTime createDate;
  private LocalDateTime updateDate;
}
