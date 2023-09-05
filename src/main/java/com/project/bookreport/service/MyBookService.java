package com.project.bookreport.service;

import static com.project.bookreport.exception.ErrorCode.BOOK_NOT_FOUND;
import static com.project.bookreport.exception.ErrorCode.MEMBER_BOOK_NOT_UNIQUE;
import static com.project.bookreport.exception.ErrorCode.MEMBER_NOT_FOUND;

import com.project.bookreport.domain.Book;
import com.project.bookreport.domain.Member;
import com.project.bookreport.domain.MyBook;
import com.project.bookreport.exception.custom_exceptions.BookException;
import com.project.bookreport.exception.custom_exceptions.MemberException;
import com.project.bookreport.model.book.BookDTO;
import com.project.bookreport.model.member.MemberContext;
import com.project.bookreport.model.myBook.MyBookRequest;
import com.project.bookreport.repository.BookRepository;
import com.project.bookreport.repository.MemberRepository;
import com.project.bookreport.repository.MyBookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
  public void saveMyBook(MemberContext memberContext, BookDTO bookDTO, MyBookRequest myBookRequest) {
    Member member = memberRepository.findMemberById(memberContext.getId())
        .orElseThrow(()->new MemberException(MEMBER_NOT_FOUND));
    Book book = bookRepository.findById(bookDTO.getId())
        .orElseThrow(() -> new BookException(BOOK_NOT_FOUND));

    if (myBookRepository.findByMemberAndBook(member, book).isPresent()) {
      throw new BookException(MEMBER_BOOK_NOT_UNIQUE);
    }

    MyBook myBook = MyBook.builder()
        .book(book)
        .member(member)
        .myBookStatus(myBookRequest.getMyBookStatus())
        .rate(myBookRequest.getRate())
        .startDate(myBookRequest.getStartDate())
        .endDate(myBookRequest.getEndDate())
        .build();
    myBookRepository.save(myBook);
  }
}
