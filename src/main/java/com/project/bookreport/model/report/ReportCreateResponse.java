package com.project.bookreport.model.report;

import com.project.bookreport.model.book.BookDTO;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ReportCreateResponse {

  private ReportDTO reportDTO;
  private BookDTO bookDTO;
}
