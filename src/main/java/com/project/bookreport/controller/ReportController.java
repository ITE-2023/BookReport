package com.project.bookreport.controller;

import com.project.bookreport.domain.Report;
import com.project.bookreport.exception.DataNotFoundException;
import com.project.bookreport.model.member.MemberContext;
import com.project.bookreport.model.report.ReportCreateRequest;
import com.project.bookreport.service.ReportService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequiredArgsConstructor
public class ReportController {
    private final ReportService reportService;

    @PostMapping("/report/create")
    public ResponseEntity<Report> create(@Valid @RequestBody ReportCreateRequest reportCreateRequest
                                         ,@AuthenticationPrincipal MemberContext memberContext){
        Report report = reportService.create(reportCreateRequest, memberContext);
        return ResponseEntity.ok(report);
    }
    @PreAuthorize("isAuthenticated")
    @DeleteMapping("/report/delete/{id}")
    public ResponseEntity delete(@AuthenticationPrincipal MemberContext memberContext, @PathVariable("id") long id) throws DataNotFoundException {
        Report report = this.reportService.getReport(id);
        if(!report.getMemberContext().getUsername().equals(memberContext.getUsername())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }
        this.reportService.delete(report);
        return ResponseEntity.ok().build();//완료한 값을 던짐
    }
}
