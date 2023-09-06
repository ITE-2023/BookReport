package com.project.bookreport.service;

import static com.project.bookreport.exception.ErrorCode.*;

import com.project.bookreport.domain.Member;
import com.project.bookreport.domain.MyBook;
import com.project.bookreport.domain.Report;
import com.project.bookreport.exception.custom_exceptions.MemberException;
import com.project.bookreport.exception.custom_exceptions.MyBookException;
import com.project.bookreport.exception.custom_exceptions.ReportException;
import com.project.bookreport.model.member.MemberContext;
import com.project.bookreport.model.report.ReportDTO;
import com.project.bookreport.model.report.ReportRequest;
import com.project.bookreport.repository.BookRepository;
import com.project.bookreport.repository.MemberRepository;
import com.project.bookreport.repository.MyBookRepository;
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
    private final BookRepository bookRepository;
    private final BookService bookService;

    private final MyBookRepository myBookRepository;

    /**
     * 독후감 생성
     */
    public ReportDTO create(MemberContext memberContext, Long myBookId, ReportRequest reportRequest) {
        Member member = memberRepository.findMemberById(memberContext.getId())
            .orElseThrow(()->new MemberException(MEMBER_NOT_FOUND));
        MyBook myBook = myBookRepository.findById(myBookId)
            .orElseThrow(() -> new MyBookException(MY_BOOK_NOT_FOUND));
        // 마이북과 회원 일치 비교

        Report report = Report.builder()
            .title(reportRequest.getTitle())
            .content(reportRequest.getContent())
            .member(member)
            .myBook(myBook)
            .build();
        Report savedReport = reportRepository.save(report);
        return ReportDTO.builder()
            .id(savedReport.getId())
            .title(savedReport.getTitle())
            .content(savedReport.getContent())
            .username(member.getUsername())
            .createDate(savedReport.getCreateDate())
            .updateDate(savedReport.getUpdateDate())
            .build();
    }

    /**
     * 독후감 수정
     */
    @Transactional
    public ReportDTO update(MemberContext memberContext, Long id, ReportRequest reportRequest) {

        Report report = findReportById(id);
        if (!report.getMember().getUsername().equals(memberContext.getUsername())) {
            throw new ReportException(ACCESS_DENIED);
        }

        report.setTitle(reportRequest.getTitle());
        report.setContent(reportRequest.getContent());
        reportRepository.save(report);
        return ReportDTO.builder()
            .id(report.getId())
            .title(report.getTitle())
            .content(report.getContent())
            .username(report.getMember().getUsername())
            .createDate(report.getCreateDate())
            .updateDate(report.getUpdateDate())
            .build();
    }

    /**
     * 독후감 삭제
     */
    public void delete(MemberContext memberContext, Long id){
        Report report = findReportById(id);

        if(!report.getMember().getUsername().equals(memberContext.getUsername())){
            throw new ReportException(ACCESS_DENIED);
        }
        reportRepository.delete(report);
    }

    /**
     * 독후감 단건 조회 후 DTO로 변환
     */
    public ReportDTO getReport(Long id) {
        Report report = findReportById(id);
        return ReportDTO.builder()
            .id(report.getId())
            .title(report.getTitle())
            .content(report.getContent())
            .username(report.getMember().getUsername())
            .createDate(report.getCreateDate())
            .updateDate(report.getUpdateDate())
            .build();
    }

    /**
     * 독후감 단건 조회
     */
    private Report findReportById(Long id) {
        return reportRepository.findById(id)
            .orElseThrow(() -> new ReportException(REPORT_NOT_FOUND));
    }

    /**
     * 독후감 전체 조회
     */
    public List<ReportDTO> getReportList(Pageable pageable) {
        Page<Report> reports = reportRepository.findAll(pageable);
        return reports.stream().map(report -> ReportDTO.builder().id(report.getId())
            .title(report.getTitle())
            .content(report.getContent())
            .username(report.getMember().getUsername())
            .createDate(report.getCreateDate())
            .updateDate(report.getUpdateDate())
            .build()).toList();
    }

    /**
     * 내 독후감 전체 조회
     */
    public List<ReportDTO> getMyReportList(MemberContext memberContext, Pageable pageable) {
        List<Report> myReport = reportRepository.findAllByMember_Username(
            memberContext.getUsername(), pageable);
        return myReport.stream().map(report -> ReportDTO.builder().id(report.getId())
            .title(report.getTitle())
            .content(report.getContent())
            .username(report.getMember().getUsername())
            .createDate(report.getCreateDate())
            .updateDate(report.getUpdateDate())
            .build()).toList();
    }
}
