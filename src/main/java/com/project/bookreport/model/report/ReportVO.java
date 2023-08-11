package com.project.bookreport.model.report;

import com.project.bookreport.model.book.BookRequest;
import lombok.Getter;

@Getter
public class ReportVO {
  private ReportRequest reportRequest;
  private BookRequest bookRequest;
}
