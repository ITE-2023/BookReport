package com.project.bookreport.service;

import static com.project.bookreport.exception.ErrorCode.*;

import com.project.bookreport.domain.Member;
import com.project.bookreport.domain.Report;
import com.project.bookreport.exception.MemberException;
import com.project.bookreport.exception.ReportException;
import com.project.bookreport.model.member.MemberContext;
import com.project.bookreport.model.report.ReportDTO;
import com.project.bookreport.model.report.ReportRequest;
import com.project.bookreport.repository.MemberRepository;
import com.project.bookreport.repository.ReportRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final ReportRepository reportRepository;
    private final MemberRepository memberRepository;

    public ReportDTO create(ReportRequest reportRequest, MemberContext memberContext) {
        Member member = memberRepository.findMemberById(memberContext.getId())
            .orElseThrow(()-> new MemberException(MEMBER_NOT_FOUND));
        Report report = Report.builder()
                .title(reportRequest.getTitle())
                .content(reportRequest.getContent())
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
        Report report = findReportById(id);

        if(!report.getMember().getUsername().equals(memberContext.getUsername())){
            throw new ReportException(ACCESS_DENIED);
        }

        reportRepository.delete(report);
    }

    @Transactional
    public ReportDTO update(Long id, ReportRequest reportRequest, MemberContext memberContext){
        Report report = findReportById(id);

        if(!report.getMember().getUsername().equals(memberContext.getUsername())){
            throw new ReportException(ACCESS_DENIED);
        }
        report.setTitle(reportRequest.getTitle());
        report.setContent(reportRequest.getContent());
        return ReportDTO.builder()
            .id(report.getId())
            .title(report.getTitle())
            .content(report.getContent())
            .createDate(report.getCreateDate())
            .updateDate(report.getUpdateDate())
            .build();
    }

    public ReportDTO getReport(Long id) {
        Report report = findReportById(id);
        return ReportDTO.builder()
            .id(report.getId())
            .title(report.getTitle())
            .content(report.getContent())
            .createDate(report.getCreateDate())
            .updateDate(report.getUpdateDate())
            .build();
    }

    private Report findReportById(Long id) {
        return reportRepository.findById(id)
            .orElseThrow(() -> new ReportException(REPORT_NOT_FOUND));
    }

    public List<ReportDTO> getReportList(Pageable pageable) {
        Page<Report> reports = reportRepository.findAll(pageable);
        return reports.stream().map(report -> ReportDTO.builder().id(report.getId())
            .title(report.getTitle())
            .content(report.getContent())
            .createDate(report.getCreateDate())
            .updateDate(report.getUpdateDate())
            .build()).toList();
    }

    public List<ReportDTO> getMyReportList(MemberContext memberContext, Pageable pageable) {
        List<Report> myReport = reportRepository.findAllByMember_Username(
            memberContext.getUsername(), pageable);
        return myReport.stream().map(report -> ReportDTO.builder().id(report.getId())
            .title(report.getTitle())
            .content(report.getContent())
            .createDate(report.getCreateDate())
            .updateDate(report.getUpdateDate())
            .build()).toList();
    }
}
