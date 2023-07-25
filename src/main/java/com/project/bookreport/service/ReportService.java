package com.project.bookreport.service;

import com.project.bookreport.domain.Member;
import com.project.bookreport.domain.Report;
import com.project.bookreport.exception.DataNotFoundException;
import com.project.bookreport.exception.ErrorCode;
import com.project.bookreport.exception.MemberException;
import com.project.bookreport.model.member.MemberContext;
import com.project.bookreport.model.report.ReportCreateRequest;
import com.project.bookreport.model.report.ReportUpdateRequest;
import com.project.bookreport.repository.MemberRepository;
import com.project.bookreport.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final ReportRepository reportRepository;
    private final MemberRepository memberRepository;

    public Report getReport(long id) throws DataNotFoundException {
        Optional<Report> report = this.reportRepository.findById(id);
        if(report.isPresent()){
            return report.get();
        }
        else{
            throw new DataNotFoundException("report not found");
        }
    }
    public Report create(ReportCreateRequest reportCreateRequest, MemberContext memberContext) {
        Member member = memberRepository.findMemberById(memberContext.getId()).orElseThrow(()-> new MemberException(ErrorCode.MEMBER_NOT_FOUND));
        Report report = Report.builder()
                .title(reportCreateRequest.getTitle())
                .content(reportCreateRequest.getContent())
                .member(member)
                .build();
        return reportRepository.save(report);
    }

    public void delete(Report report){this.reportRepository.delete(report);}

    @Transactional
    public Report modify(Report report, ReportUpdateRequest reportUpdateRequest, MemberContext memberContext){
        if(!report.getMember().getUsername().equals(memberContext.getUsername())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        report.setTitle(reportUpdateRequest.getTitle());
        report.setContent(reportUpdateRequest.getContent());
        return report;
    }
}
