package com.project.bookreport.service;

import com.project.bookreport.domain.Report;
import com.project.bookreport.model.report.ReportCreateRequest;
import com.project.bookreport.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final ReportRepository reportRepository;
    public Report create(ReportCreateRequest reportCreateRequest) {
        Report report = Report.builder()
                .title(reportCreateRequest.getTitle())
                .build();
        return reportRepository.save(report);
    }
}
