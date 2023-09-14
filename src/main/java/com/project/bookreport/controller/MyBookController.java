package com.project.bookreport.controller;

import static org.springframework.data.domain.Sort.Direction.DESC;

import com.project.bookreport.model.book.BookDTO;
import com.project.bookreport.model.member.MemberContext;
import com.project.bookreport.model.myBook.MyBookDTO;
import com.project.bookreport.model.myBook.MyBookRequest;
import com.project.bookreport.model.myBook.MyBookResponse;
import com.project.bookreport.model.myBook.MyBookVO;
import com.project.bookreport.service.BookService;
import com.project.bookreport.service.MyBookService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
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

  /**
   * 내 서재에 담긴 책 삭제
   */
  @DeleteMapping("/myBook/delete/{id}")
  public ResponseEntity<Object> deleteMyBook(@AuthenticationPrincipal MemberContext memberContext,
      @PathVariable("id") Long id) {
    myBookService.delete(memberContext, id);
    return ResponseEntity.ok().build();
  }

  /**
   * 내 서재에 담긴 책 리스트 조회
   */
  @GetMapping("/myBooks")
  public ResponseEntity<List<MyBookResponse>> myBooks(
      @AuthenticationPrincipal MemberContext memberContext,
      @PageableDefault(sort = "id", direction = DESC, size = 10)
      Pageable pageable, @RequestParam("year") Integer year) {
    List<MyBookResponse> myBookResponses = myBookService.myBooks(memberContext, pageable, year);
    return ResponseEntity.ok(myBookResponses);
  }

  /**
   * 내 서재에 담긴 책인지 여부 확인
   */
  @GetMapping("/myBook/check/{isbn}")
  public ResponseEntity<Boolean> checkMyBook(@AuthenticationPrincipal MemberContext memberContext,
      @PathVariable("isbn") String isbn) {
    Boolean b = myBookService.checkMyBook(memberContext, isbn);
    return ResponseEntity.ok(b);
  }
}
