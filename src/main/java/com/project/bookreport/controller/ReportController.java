package com.project.bookreport.controller;

import static org.springframework.data.domain.Sort.Direction.DESC;

import com.project.bookreport.model.member.MemberContext;
import com.project.bookreport.model.report.ReportDTO;
import com.project.bookreport.model.report.ReportPagingResponse;
import com.project.bookreport.model.report.ReportRequest;
import com.project.bookreport.service.ReportService;
import jakarta.validation.Valid;
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
     * 독후감 생성
     */
    @PostMapping("/report/create/{myBookId}")
    public ResponseEntity<ReportDTO> create(@AuthenticationPrincipal MemberContext memberContext,
        @PathVariable("myBookId") Long myBookId, @Valid @RequestBody ReportRequest reportRequest) {
        ReportDTO reportDTO = reportService.create(memberContext, myBookId, reportRequest);
        return ResponseEntity.ok(reportDTO);
    }

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
     * 내 서재로 독후감 조회
     */
    @GetMapping("/report/detail")
    public ResponseEntity<ReportDTO> getReport(@AuthenticationPrincipal MemberContext memberContext,
        @RequestParam("myBook") Long myBookId) {
        ReportDTO reportDTO = reportService.getReport(memberContext, myBookId);
        return ResponseEntity.ok(reportDTO);
    }

    /**
     * 독후감 단 건 조회
     */
    @GetMapping("/report/detail/{id}")
    public ResponseEntity<ReportDTO> getReportById(@PathVariable("id") Long id) {
        ReportDTO reportById = reportService.getReportById(id);
        return ResponseEntity.ok(reportById);
    }

    /**
     * 책별 독후감 조회
     */
    @GetMapping("/reports/{isbn}")
    public ResponseEntity<ReportPagingResponse> getReportList(@PathVariable("isbn") String isbn,
        @PageableDefault(sort = "id", direction = DESC, size = 10)
        Pageable pageable) {
        ReportPagingResponse reportList = reportService.getReportList(isbn, pageable);
        return ResponseEntity.ok(reportList);
    }
}
