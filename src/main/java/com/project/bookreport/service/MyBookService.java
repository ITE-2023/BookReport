package com.project.bookreport.service;

import static com.project.bookreport.exception.ErrorCode.ACCESS_DENIED;
import static com.project.bookreport.exception.ErrorCode.BOOK_NOT_FOUND;
import static com.project.bookreport.exception.ErrorCode.MEMBER_NOT_FOUND;
import static com.project.bookreport.exception.ErrorCode.MY_BOOK_NOT_FOUND;
import static com.project.bookreport.exception.ErrorCode.MY_BOOK_NOT_UNIQUE;

import com.project.bookreport.domain.Book;
import com.project.bookreport.domain.Member;
import com.project.bookreport.domain.MyBook;
import com.project.bookreport.exception.custom_exceptions.BookException;
import com.project.bookreport.exception.custom_exceptions.MemberException;
import com.project.bookreport.exception.custom_exceptions.MyBookException;
import com.project.bookreport.model.book.BookDTO;
import com.project.bookreport.model.member.MemberContext;
import com.project.bookreport.model.myBook.MyBookCheck;
import com.project.bookreport.model.myBook.MyBookDTO;
import com.project.bookreport.model.myBook.MyBookPagingResponse;
import com.project.bookreport.model.myBook.MyBookRequest;
import com.project.bookreport.model.myBook.MyBookResponse;
import com.project.bookreport.repository.BookRepository;
import com.project.bookreport.repository.MemberRepository;
import com.project.bookreport.repository.MyBookRepository;
import com.project.bookreport.repository.ReportRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MyBookService {

  private final MemberRepository memberRepository;
  private final BookRepository bookRepository;
  private final MyBookRepository myBookRepository;
  private final ReportService reportService;
  private final ReportRepository reportRepository;

  /**
   * 내 서재에 추가
   * - Member와 Book 연결
   * - 이미 서재에 담겨 있는지 확인
   */
  public MyBookDTO saveMyBook(MemberContext memberContext, BookDTO bookDTO, MyBookRequest myBookRequest) {
    Member member = memberRepository.findMemberById(memberContext.getId())
        .orElseThrow(()->new MemberException(MEMBER_NOT_FOUND));
    Book book = bookRepository.findById(bookDTO.getId())
        .orElseThrow(() -> new BookException(BOOK_NOT_FOUND));

    if (myBookRepository.findByMemberAndBook(member, book).isPresent()) {
      throw new BookException(MY_BOOK_NOT_UNIQUE);
    }

    if (myBookRequest.getMyBookStatus().equals("읽은 책")) {
      myBookRequest.setReadPage(null);
      myBookRequest.setReadingStartDate(null);
      myBookRequest.setExpectation(null);
    } else if (myBookRequest.getMyBookStatus().equals("읽는 중인 책")) {
      myBookRequest.setRate(null);
      myBookRequest.setStartDate(null);
      myBookRequest.setEndDate(null);
      myBookRequest.setExpectation(null);
    } else {
      myBookRequest.setRate(null);
      myBookRequest.setStartDate(null);
      myBookRequest.setEndDate(null);
      myBookRequest.setReadPage(null);
      myBookRequest.setReadingStartDate(null);
    }

    MyBook myBook = MyBook.builder()
        .book(book)
        .member(member)
        .report(null)
        .myBookStatus(myBookRequest.getMyBookStatus())
        .rate(myBookRequest.getRate())
        .startDate(myBookRequest.getStartDate())
        .endDate(myBookRequest.getEndDate())
        .readPage(myBookRequest.getReadPage())
        .readingStartDate(myBookRequest.getReadingStartDate())
        .expectation(myBookRequest.getExpectation())
        .build();
    MyBook saveMyBook = myBookRepository.save(myBook);
    return MyBookDTO.builder()
        .id(saveMyBook.getId())
        .myBookStatus(saveMyBook.getMyBookStatus())
        .rate(saveMyBook.getRate())
        .startDate(saveMyBook.getStartDate())
        .endDate(saveMyBook.getEndDate())
        .readPage(saveMyBook.getReadPage())
        .readingStartDate(saveMyBook.getReadingStartDate())
        .expectation(saveMyBook.getExpectation())
        .createDate(saveMyBook.getCreateDate())
        .updateDate(saveMyBook.getUpdateDate())
        .build();
  }

  /**
   * 내 서재 속 책 상태 수정
   */
  @Transactional
  public MyBookDTO update(MemberContext memberContext, Long id, MyBookRequest myBookRequest) {
    MyBook myBook = myBookRepository.findById(id)
        .orElseThrow(() -> new MyBookException(MY_BOOK_NOT_FOUND));

    if (!myBook.getMember().getUsername().equals(memberContext.getUsername())) {
      throw new MyBookException(ACCESS_DENIED);
    }

    myBook.setMyBookStatus(myBookRequest.getMyBookStatus());
    myBook.setRate(myBookRequest.getRate());
    myBook.setStartDate(myBookRequest.getStartDate());
    myBook.setEndDate(myBookRequest.getEndDate());
    myBook.setReadPage(myBookRequest.getReadPage());
    myBook.setReadingStartDate(myBookRequest.getReadingStartDate());
    myBook.setExpectation(myBookRequest.getExpectation());
    return MyBookDTO.builder()
        .id(myBook.getId())
        .myBookStatus(myBook.getMyBookStatus())
        .rate(myBook.getRate())
        .startDate(myBook.getStartDate())
        .endDate(myBook.getEndDate())
        .createDate(myBook.getCreateDate())
        .updateDate(myBook.getUpdateDate())
        .readPage(myBook.getReadPage())
        .readingStartDate(myBook.getReadingStartDate())
        .expectation(myBook.getExpectation())
        .build();
  }

  /**
   * 내 서재에서 삭제
   */
  public void delete(MemberContext memberContext, Long id) {
    MyBook myBook = myBookRepository.findById(id)
        .orElseThrow(() -> new MyBookException(MY_BOOK_NOT_FOUND));

    if (!myBook.getMember().getUsername().equals(memberContext.getUsername())) {
      throw new MyBookException(ACCESS_DENIED);
    }

    myBookRepository.delete(myBook);
  }

  /**
   * 내 서재 속 책 리스트 조회
   */
  public MyBookPagingResponse myBooks(MemberContext memberContext, Pageable pageable, Integer year) {
    Member member = memberRepository.findMemberById(memberContext.getId())
        .orElseThrow(()->new MemberException(MEMBER_NOT_FOUND));
    Page<MyBook> myBooks = myBookRepository.findAllByMember(pageable, member, year);
    int totalPage = myBooks.getTotalPages();
    List<MyBookResponse> myBookResponses = myBooks.stream()
        .map(myBook -> {
          Book book = myBook.getBook();
          BookDTO bookDto = BookDTO.builder()
              .id(book.getId())
              .isbn(book.getIsbn())
              .bookName(book.getBookName())
              .author(book.getAuthor())
              .publisher(book.getPublisher())
              .imageUrl(book.getImageUrl())
              .createDate(book.getCreateDate())
              .updateDate(book.getUpdateDate())
              .build();

          MyBookDTO myBookDTO = MyBookDTO.builder()
              .id(myBook.getId())
              .myBookStatus(myBook.getMyBookStatus())
              .rate(myBook.getRate())
              .startDate(myBook.getStartDate())
              .endDate(myBook.getEndDate())
              .readPage(myBook.getReadPage())
              .readingStartDate(myBook.getReadingStartDate())
              .expectation(myBook.getExpectation())
              .createDate(myBook.getCreateDate())
              .updateDate(myBook.getUpdateDate())
              .build();

          return MyBookResponse.builder()
              .bookDTO(bookDto)
              .myBookDTO(myBookDTO)
              .build();
        }).toList();
    return MyBookPagingResponse.builder()
        .totalPage(totalPage)
        .myBooks(myBookResponses)
        .build();
  }

  public MyBookCheck checkMyBook(MemberContext memberContext, String isbn) {
    boolean check = false;
    Long id = null;
    Optional<Book> book = bookRepository.findByIsbn(isbn);
    if (book.isEmpty()) {
      return MyBookCheck.builder().check(check).myBookId(id).build();
    }
    Member member = memberRepository.findMemberById(memberContext.getId())
        .orElseThrow(() -> new MemberException(MEMBER_NOT_FOUND));
    Optional<MyBook> myBook = myBookRepository.findByMemberAndBook(member, book.get());
    if (myBook.isPresent()) {
      check = true;
      id = myBook.get().getId();
    }
    return MyBookCheck.builder().check(check).myBookId(id).build();
  }
}
