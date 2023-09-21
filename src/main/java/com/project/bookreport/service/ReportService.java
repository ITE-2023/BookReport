package com.project.bookreport.service;

import static com.project.bookreport.exception.ErrorCode.*;

import com.project.bookreport.domain.Book;
import com.project.bookreport.domain.Member;
import com.project.bookreport.domain.MyBook;
import com.project.bookreport.domain.Report;
import com.project.bookreport.exception.custom_exceptions.BookException;
import com.project.bookreport.exception.custom_exceptions.MemberException;
import com.project.bookreport.exception.custom_exceptions.MyBookException;
import com.project.bookreport.exception.custom_exceptions.ReportException;
import com.project.bookreport.model.member.MemberContext;
import com.project.bookreport.model.report.ReportDTO;
import com.project.bookreport.model.report.ReportPagingResponse;
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
    private final MyBookRepository myBookRepository;
    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;

    /**
     * 독후감 생성
     */
    @Transactional
    public ReportDTO create(MemberContext memberContext, Long myBookId,
        ReportRequest reportRequest) {
        MyBook myBook = myBookRepository.findById(myBookId)
            .orElseThrow(() -> new MyBookException(MY_BOOK_NOT_FOUND));
        if (!myBook.getMember().getUsername().equals(memberContext.getUsername())) {
            throw new ReportException(ACCESS_DENIED);
        }
        Book book = myBook.getBook();
        Member member = memberRepository.findMemberById(memberContext.getId())
            .orElseThrow(()->new MemberException(MEMBER_NOT_FOUND));
        Report report = Report.builder()
            .title(reportRequest.getTitle())
            .content(reportRequest.getContent())
            .username(member.getUsername())
            .book(book)
            .build();
        Report savedReport = reportRepository.save(report);
        myBook.setReport(report);
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
        if (!report.getUsername().equals(memberContext.getUsername())) {
            throw new ReportException(ACCESS_DENIED);
        }

        report.setTitle(reportRequest.getTitle());
        report.setContent(reportRequest.getContent());
        reportRepository.save(report);
        return ReportDTO.builder()
            .id(report.getId())
            .title(report.getTitle())
            .content(report.getContent())
            .username(report.getUsername())
            .createDate(report.getCreateDate())
            .updateDate(report.getUpdateDate())
            .build();
    }

    /**
     * 독후감 단건 조회
     */
    public ReportDTO getReport(Long myBookId) {
        MyBook myBook = myBookRepository.findById(myBookId)
            .orElseThrow(() -> new MyBookException(MY_BOOK_NOT_FOUND));
        Report report = myBook.getReport();
        return ReportDTO.builder()
            .id(report.getId())
            .title(report.getTitle())
            .content(report.getContent())
            .username(report.getUsername())
            .createDate(report.getCreateDate())
            .updateDate(report.getUpdateDate())
            .build();
    }

    /**
     * 독후감 조회
     */
    private Report findReportById(Long id) {
        return reportRepository.findById(id)
            .orElseThrow(() -> new ReportException(REPORT_NOT_FOUND));
    }

    /**
     * 책별 독후감 리스트 페이징
     */
    public ReportPagingResponse getReportList(String isbn, Pageable pageable) {
        Book book = bookRepository.findByIsbn(isbn)
            .orElseThrow(() -> new BookException(BOOK_NOT_FOUND));

        Page<Report> reports = reportRepository.findAllByBook(book, pageable);
        int totalPage = reports.getTotalPages();
        List<ReportDTO> reportDTOS = reports.stream().map(report ->
            ReportDTO.builder()
                .id(report.getId())
                .title(report.getTitle())
                .content(report.getContent())
                .username(report.getUsername())
                .createDate(report.getCreateDate())
                .updateDate(report.getUpdateDate())
                .build()
        ).toList();
        return ReportPagingResponse.builder().totalPage(totalPage).reportList(reportDTOS).build();
    }
}
