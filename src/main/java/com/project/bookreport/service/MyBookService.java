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
import com.project.bookreport.model.myBook.MyBookDTO;
import com.project.bookreport.model.myBook.MyBookRequest;
import com.project.bookreport.repository.BookRepository;
import com.project.bookreport.repository.MemberRepository;
import com.project.bookreport.repository.MyBookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MyBookService {

  private final MemberRepository memberRepository;
  private final BookRepository bookRepository;
  private final MyBookRepository myBookRepository;

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

    MyBook myBook = MyBook.builder()
        .book(book)
        .member(member)
        .myBookStatus(myBookRequest.getMyBookStatus())
        .rate(myBookRequest.getRate())
        .startDate(myBookRequest.getStartDate())
        .endDate(myBookRequest.getEndDate())
        .build();
    MyBook saveMyBook = myBookRepository.save(myBook);
    return MyBookDTO.builder()
        .id(saveMyBook.getId())
        .myBookStatus(saveMyBook.getMyBookStatus())
        .rate(saveMyBook.getRate())
        .startDate(saveMyBook.getStartDate())
        .endDate(saveMyBook.getEndDate())
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
    return MyBookDTO.builder()
        .id(myBook.getId())
        .myBookStatus(myBook.getMyBookStatus())
        .rate(myBook.getRate())
        .startDate(myBook.getStartDate())
        .endDate(myBook.getEndDate())
        .createDate(myBook.getCreateDate())
        .updateDate(myBook.getUpdateDate())
        .build();
  }
}
