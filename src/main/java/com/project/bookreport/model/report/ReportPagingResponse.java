package com.project.bookreport.model.report;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ReportPagingResponse {
  private int totalPage;
  private List<ReportDTO> reportList;
}
