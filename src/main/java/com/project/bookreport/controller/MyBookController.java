package com.project.bookreport.controller;

import com.project.bookreport.model.book.BookDTO;
import com.project.bookreport.model.member.MemberContext;
import com.project.bookreport.model.myBook.MyBookDTO;
import com.project.bookreport.model.myBook.MyBookRequest;
import com.project.bookreport.model.myBook.MyBookVO;
import com.project.bookreport.service.BookService;
import com.project.bookreport.service.MyBookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MyBookController {

  private final BookService bookService;
  private final MyBookService myBookService;

  /**
   * 내 서재에 담기
   */
  @PostMapping("/myBook/save")
  public ResponseEntity<MyBookDTO> saveMyBook(@AuthenticationPrincipal MemberContext memberContext,
      @Valid @RequestBody MyBookVO myBookVO) {
    BookDTO bookDTO = bookService.create(myBookVO.getBookRequest());
    MyBookDTO myBookDTO = myBookService.saveMyBook(memberContext, bookDTO,
        myBookVO.getMyBookRequest());
    return ResponseEntity.ok(myBookDTO);
  }

  /**
   * 내 서재 속 책 상태 수정
   */
  @PostMapping("/myBook/update/{id}")
  public ResponseEntity<MyBookDTO> updateMyBook(
      @AuthenticationPrincipal MemberContext memberContext,
      @PathVariable("id") Long id, @Valid @RequestBody MyBookRequest myBookRequest) {
    MyBookDTO myBookDTO = myBookService.update(memberContext, id, myBookRequest);
    return ResponseEntity.ok(myBookDTO);
  }
}
