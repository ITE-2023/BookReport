package com.project.bookreport.service;

import static com.project.bookreport.exception.ErrorCode.*;

import com.project.bookreport.domain.Book;
import com.project.bookreport.domain.Member;
import com.project.bookreport.domain.Report;
import com.project.bookreport.exception.BookException;
import com.project.bookreport.exception.MemberException;
import com.project.bookreport.exception.ReportException;
import com.project.bookreport.model.book.BookDTO;
import com.project.bookreport.model.member.MemberContext;
import com.project.bookreport.model.report.ReportDTO;
import com.project.bookreport.model.report.ReportRequest;
import com.project.bookreport.repository.BookRepository;
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
    private final BookRepository bookRepository;

    public ReportDTO create(MemberContext memberContext, ReportRequest reportRequest, BookDTO bookDTO) {
        Member member = memberRepository.findMemberById(memberContext.getId())
            .orElseThrow(()->new MemberException(MEMBER_NOT_FOUND));
        Book book = bookRepository.findById(bookDTO.getId())
            .orElseThrow(() ->new BookException(BOOK_NOT_FOUND));

        Report report = Report.builder()
            .title(reportRequest.getTitle())
            .content(reportRequest.getContent())
            .member(member)
            .book(book)
            .build();
        Report savedReport = reportRepository.save(report);
        return ReportDTO.builder()
            .id(savedReport.getId())
            .title(savedReport.getTitle())
            .content(savedReport.getContent())
            .memberId(member.getId())
            .bookId(book.getId())
            .createDate(savedReport.getCreateDate())
            .updateDate(savedReport.getUpdateDate())
            .build();
    }

    @Transactional
    public ReportDTO update(MemberContext memberContext, Long id, ReportRequest reportRequest,
        BookDTO bookDTO) {

        Report report = findReportById(id);
        if (!report.getMember().getUsername().equals(memberContext.getUsername())) {
            throw new ReportException(ACCESS_DENIED);
        }
        Book book = bookRepository.findById(bookDTO.getId())
            .orElseThrow(() ->new BookException(BOOK_NOT_FOUND));

        report.setTitle(reportRequest.getTitle());
        report.setContent(reportRequest.getContent());
        report.setBook(book);
        return ReportDTO.builder()
            .id(report.getId())
            .title(report.getTitle())
            .content(report.getContent())
            .memberId(report.getMember().getId())
            .bookId(bookDTO.getId())
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

    public void delete(MemberContext memberContext, Long id){
        Report report = findReportById(id);

        if(!report.getMember().getUsername().equals(memberContext.getUsername())){
            throw new ReportException(ACCESS_DENIED);
        }

        reportRepository.delete(report);
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
