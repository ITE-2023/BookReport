package com.project.bookreport.controller;

import static org.springframework.data.domain.Sort.Direction.DESC;

import com.project.bookreport.model.member.MemberContext;
import com.project.bookreport.model.report.ReportDTO;
import com.project.bookreport.model.report.ReportRequest;
import com.project.bookreport.service.ReportService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import org.springframework.web.bind.annotation.*;

/**
 * 독후감 API
 */
@RestController
@RequiredArgsConstructor
public class ReportController {
    private final ReportService reportService;

    /**
     * 독후감 수정
     */
    @PatchMapping("/report/update/{id}")
    public ResponseEntity<ReportDTO> update(
        @AuthenticationPrincipal MemberContext memberContext,
        @PathVariable("id") Long id,
        @Valid @RequestBody ReportRequest reportRequest){
        ReportDTO reportDTO = reportService.update(memberContext, id, reportRequest);
        return ResponseEntity.ok(reportDTO);
    }

    /**
     * 독후감 조회
     */
    @GetMapping("/report/detail/{id}")
    public ResponseEntity<ReportDTO> getReport(@PathVariable("id") Long myBookId) {
        ReportDTO reportDTO = reportService.getReport(myBookId);
        return ResponseEntity.ok(reportDTO);
    }

    /**
     * 책별 독후감 조회
     */
    @GetMapping("/reports/{isbn}")
    public ResponseEntity<List<ReportDTO>> getReportList(@PathVariable("isbn") String isbn,
        @PageableDefault(sort = "id", direction = DESC, size = 10)
        Pageable pageable) {
        List<ReportDTO> reportList = reportService.getReportList(isbn, pageable);
        return ResponseEntity.ok(reportList);
    }
}
