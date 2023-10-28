package com.project.bookreport.model.report;

import com.project.bookreport.domain.status.EmotionType;
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
  private EmotionType emotionType;
  private LocalDateTime createDate;
  private LocalDateTime updateDate;
}
