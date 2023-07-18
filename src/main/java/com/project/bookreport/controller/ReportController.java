package com.project.bookreport.controller;

import com.project.bookreport.domain.Report;
import com.project.bookreport.model.report.ReportCreateRequest;
import com.project.bookreport.service.ReportService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReportController {
    private final ReportService reportService;

    @PostMapping("/report/create")
    public ResponseEntity<Report> create(@Valid @RequestBody ReportCreateRequest reportCreateRequest){
        Report report = reportService.create(reportCreateRequest);
        return ResponseEntity.ok(report);
    }
}
