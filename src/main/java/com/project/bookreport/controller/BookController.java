package com.project.bookreport.controller;

import com.project.bookreport.model.book.BookDTO;
import com.project.bookreport.model.book.BookRequest;
import com.project.bookreport.model.book.BookSearchDTO;
import com.project.bookreport.model.member.MemberContext;
import com.project.bookreport.service.BookService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

/**
 * 책 API
 */
@RestController
@RequiredArgsConstructor
public class BookController {

  private final BookService bookService;

  /**
   * 책 생성 (admin)
   */
  @PostMapping("/book/create")
  public ResponseEntity<BookDTO> create(@Valid @RequestBody BookRequest bookRequest) {
    BookDTO bookDTO = bookService.create(bookRequest);
    return ResponseEntity.ok(bookDTO);
  }

  /**
   * 책 수정 (admin)
   */
  @PatchMapping("/book/update/{id}")
  public ResponseEntity<BookDTO> update(@Valid @RequestBody BookRequest bookRequest,
      @PathVariable("id") Long id) {
    BookDTO bookDTO = bookService.update(bookRequest, id);
    return ResponseEntity.ok(bookDTO);
  }

  /**
   * 책 삭제 (admin)
   */
  @DeleteMapping("/book/delete/{id}")
  public ResponseEntity<Object> delete(@PathVariable("id") Long id) {
    bookService.delete(id);
    return ResponseEntity.ok().build();
  }


  /**
   * 책 검색
   */
  @GetMapping("/book/search")
  public ResponseEntity<BookSearchDTO> search(@RequestParam("query") String keyword) {
    BookSearchDTO bookSearchDTO = bookService.search(keyword);
    return ResponseEntity.ok(bookSearchDTO);
  }

  /**
   * 내 서재에서 삭제
   */
  @DeleteMapping("/myBook/delete/{isbn}")
  public ResponseEntity<Object> deleteMyBook(@AuthenticationPrincipal MemberContext memberContext,
      @PathVariable("isbn") String isbn) {
    bookService.deleteMyBook(memberContext, isbn);
    return ResponseEntity.ok().build();
  }

  /**
   * 내 서재 조회
   */
  @GetMapping("/myBook")
  public ResponseEntity<List<BookDTO>> findMyBooks(
      @AuthenticationPrincipal MemberContext memberContext) {
    List<BookDTO> myBooks = bookService.findMyBooks(memberContext);
    return ResponseEntity.ok(myBooks);
  }
}
