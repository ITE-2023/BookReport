package com.project.bookreport.controller;

import com.project.bookreport.model.member.MemberContext;
import com.project.bookreport.model.report.ReportCreateRequest;
import com.project.bookreport.model.report.ReportDTO;
import com.project.bookreport.model.report.ReportUpdateRequest;
import com.project.bookreport.service.ReportService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
public class ReportController {
    private final ReportService reportService;

    @PostMapping("/report/create")
    public ResponseEntity<ReportDTO> create(@Valid @RequestBody ReportCreateRequest reportCreateRequest
                                         ,@AuthenticationPrincipal MemberContext memberContext){
        ReportDTO reportDTO = reportService.create(reportCreateRequest, memberContext);
        return ResponseEntity.ok(reportDTO);
    }
//    @PreAuthorize("isAuthenticated")
//    @DeleteMapping("/report/delete/{id}")
//    public ResponseEntity delete(@AuthenticationPrincipal MemberContext memberContext, @PathVariable("id") long id) throws DataNotFoundException {
//        Report report = this.reportService.getReport(id);
//        if(!report.getMember().getUsername().equals(memberContext.getUsername())){
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
//        }
//        this.reportService.delete(report);
//        return ResponseEntity.ok().build();//완료한 값을 던짐
//    }

    @PostMapping("/report/update/{id}")
    public ResponseEntity<ReportDTO> update(@Valid @RequestBody ReportUpdateRequest reportUpdateRequest,
                                         @AuthenticationPrincipal MemberContext memberContext, @PathVariable("id") Long id){
        ReportDTO reportDTO = reportService.update(id, reportUpdateRequest, memberContext);
        return ResponseEntity.ok(reportDTO);
    }
}
