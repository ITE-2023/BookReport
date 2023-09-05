package com.project.bookreport.controller;

import com.project.bookreport.model.book.BookDTO;
import com.project.bookreport.model.member.MemberContext;
import com.project.bookreport.model.myBook.MyBookVO;
import com.project.bookreport.service.BookService;
import com.project.bookreport.service.MyBookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
  public ResponseEntity<Object> saveMyBook(@AuthenticationPrincipal MemberContext memberContext,
      @Valid @RequestBody MyBookVO myBookVO) {
    BookDTO bookDTO = bookService.create(myBookVO.getBookRequest());
    myBookService.saveMyBook(memberContext, bookDTO, myBookVO.getMyBookRequest());
    return ResponseEntity.ok().build();
  }
}
