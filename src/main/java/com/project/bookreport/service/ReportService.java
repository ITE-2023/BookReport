package com.project.bookreport.service;

import static com.project.bookreport.exception.ErrorCode.*;

import com.project.bookreport.domain.Member;
import com.project.bookreport.domain.Report;
import com.project.bookreport.exception.MemberException;
import com.project.bookreport.exception.ReportException;
import com.project.bookreport.model.member.MemberContext;
import com.project.bookreport.model.report.ReportCreateRequest;
import com.project.bookreport.model.report.ReportDTO;
import com.project.bookreport.model.report.ReportUpdateRequest;
import com.project.bookreport.repository.MemberRepository;
import com.project.bookreport.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final ReportRepository reportRepository;
    private final MemberRepository memberRepository;

    public ReportDTO create(ReportCreateRequest reportCreateRequest, MemberContext memberContext) {
        Member member = memberRepository.findMemberById(memberContext.getId())
            .orElseThrow(()-> new MemberException(MEMBER_NOT_FOUND));
        Report report = Report.builder()
                .title(reportCreateRequest.getTitle())
                .content(reportCreateRequest.getContent())
                .member(member)
                .build();
        Report savedReport = reportRepository.save(report);
        return ReportDTO.builder()
            .id(savedReport.getId())
            .title(savedReport.getTitle())
            .content(savedReport.getContent())
            .createDate(savedReport.getCreateDate())
            .updateDate(savedReport.getUpdateDate())
            .build();
    }

    public void delete(MemberContext memberContext, Long id){
        Report report = reportRepository.findById(id)
            .orElseThrow(() -> new ReportException(REPORT_NOT_FOUND));
        if(!report.getMember().getUsername().equals(memberContext.getUsername())){
            throw new ReportException(ACCESS_DENIED);
        }
        reportRepository.delete(report);
    }

    @Transactional
    public ReportDTO update(Long id, ReportUpdateRequest reportUpdateRequest, MemberContext memberContext){
        Report report = reportRepository.findById(id)
            .orElseThrow(() -> new ReportException(REPORT_NOT_FOUND));

        if(!report.getMember().getUsername().equals(memberContext.getUsername())){
            throw new ReportException(ACCESS_DENIED);
        }
        report.setTitle(reportUpdateRequest.getTitle());
        report.setContent(reportUpdateRequest.getContent());
        return ReportDTO.builder()
            .id(report.getId())
            .title(report.getTitle())
            .content(report.getContent())
            .createDate(report.getCreateDate())
            .updateDate(report.getUpdateDate())
            .build();
    }
}
