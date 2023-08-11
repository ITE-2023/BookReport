package com.project.bookreport.controller;

import static org.springframework.data.domain.Sort.Direction.*;

import com.project.bookreport.model.book.BookDTO;
import com.project.bookreport.model.member.MemberContext;
import com.project.bookreport.model.report.ReportRequest;
import com.project.bookreport.model.report.ReportResponse;
import com.project.bookreport.model.report.ReportVO;
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


@RestController
@RequiredArgsConstructor
public class ReportController {
    private final ReportService reportService;
    private final BookService bookService;

    @PostMapping("/report/create")
    public ResponseEntity<ReportResponse> create(@AuthenticationPrincipal MemberContext memberContext,
        @Valid @RequestBody ReportVO reportVO){
        BookDTO bookDTO = bookService.create(reportVO.getBookRequest());
        ReportDTO reportDTO = reportService.create(memberContext, reportVO.getReportRequest(), bookDTO);
        return ResponseEntity.ok(
            ReportResponse
                .builder()
                .bookDTO(bookDTO)
                .reportDTO(reportDTO)
                .build());
    }

    @DeleteMapping("/report/delete/{id}")
    public ResponseEntity<Object> delete(@AuthenticationPrincipal MemberContext memberContext, @PathVariable("id") Long id) {
        reportService.delete(memberContext, id);
        return ResponseEntity.ok().build();//완료한 값을 던짐
    }

    @PostMapping("/report/update/{id}")
    public ResponseEntity<ReportDTO> update(@Valid @RequestBody ReportRequest reportRequest,
                                         @AuthenticationPrincipal MemberContext memberContext, @PathVariable("id") Long id){
        ReportDTO reportDTO = reportService.update(id, reportRequest, memberContext);
        return ResponseEntity.ok(reportDTO);
    }

    @GetMapping("/report/{id}")
    public ResponseEntity<ReportDTO> getReport(@PathVariable Long id) {
        ReportDTO reportDTO = reportService.getReport(id);
        return ResponseEntity.ok(reportDTO);
    }

    @GetMapping("/reports")
    public ResponseEntity<List<ReportDTO>> getReportList(
        @PageableDefault(sort = "id", direction = DESC, size = 10)
        Pageable pageable) {
        List<ReportDTO> reportList = reportService.getReportList(pageable);
        return ResponseEntity.ok(reportList);
    }

    @GetMapping("/myReports")
    public ResponseEntity<List<ReportDTO>> getMyReportList(
        @AuthenticationPrincipal MemberContext memberContext,
        @PageableDefault(sort = "id", direction = DESC, size = 10) Pageable pageable
    ) {
        List<ReportDTO> myReportList = reportService.getMyReportList(memberContext, pageable);
        return ResponseEntity.ok(myReportList);
    }
}
