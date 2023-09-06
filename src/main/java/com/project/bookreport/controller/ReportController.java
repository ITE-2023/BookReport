package com.project.bookreport.controller;

import static org.springframework.data.domain.Sort.Direction.*;

import com.project.bookreport.model.member.MemberContext;
import com.project.bookreport.model.report.ReportRequest;
import com.project.bookreport.model.report.ReportDTO;
import com.project.bookreport.service.BookService;
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
    private final BookService bookService;

    /**
     * 독후감 생성
     */
    @PostMapping("/report/create/{id}")
    public ResponseEntity<ReportDTO> create(@AuthenticationPrincipal MemberContext memberContext,
        @PathVariable("id") Long myBookId,
        @Valid @RequestBody ReportRequest reportRequest){
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
     * 독후감 삭제
     */
    @DeleteMapping("/report/delete/{id}")
    public ResponseEntity<Object> delete(@AuthenticationPrincipal MemberContext memberContext, @PathVariable("id") Long id) {
        reportService.delete(memberContext, id);
        return ResponseEntity.ok().build();//완료한 값을 던짐
    }

    /**
     * 독후감 조회
     */
    @GetMapping("/report/detail/{id}")
    public ResponseEntity<ReportDTO> getReport(@PathVariable("id") Long id) {
        ReportDTO reportDTO = reportService.getReport(id);
        return ResponseEntity.ok(reportDTO);
    }

    /**
     * 독후감 전체 조회 (추후, 책별로 조회하는 거로 변경 예정)
     */
    @GetMapping("/reports")
    public ResponseEntity<List<ReportDTO>> getReportList(
        @PageableDefault(sort = "id", direction = DESC, size = 10)
        Pageable pageable) {
        List<ReportDTO> reportList = reportService.getReportList(pageable);
        return ResponseEntity.ok(reportList);
    }

    /**
     * 내 독후감 전체 조회
     */
    @GetMapping("/myReports")
    public ResponseEntity<List<ReportDTO>> getMyReportList(
        @AuthenticationPrincipal MemberContext memberContext,
        @PageableDefault(sort = "id", direction = DESC, size = 10) Pageable pageable
    ) {
        List<ReportDTO> myReportList = reportService.getMyReportList(memberContext, pageable);
        return ResponseEntity.ok(myReportList);
    }
}
