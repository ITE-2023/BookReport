package com.project.bookreport.model.report;

import com.project.bookreport.model.book.BookCreateRequest;
import lombok.Getter;

@Getter
public class ReportCreateVO {
  private ReportCreateRequest reportCreateRequest;
  private BookCreateRequest bookCreateRequest;
}
