package com.project.bookreport.controller;

import com.project.bookreport.model.book.BookDTO;
import com.project.bookreport.model.book.BookRequest;
import com.project.bookreport.model.book.BookSearchDTO;
import com.project.bookreport.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 책 API
 */
@RestController
@RequiredArgsConstructor
public class BookController {

  private final BookService bookService;

  /**
   * 책 생성
   */
  @PostMapping("/book/create")
  public ResponseEntity<BookDTO> create(@Valid @RequestBody BookRequest bookRequest) {
    BookDTO bookDTO = bookService.create(bookRequest);
    return ResponseEntity.ok(bookDTO);
  }

  @DeleteMapping("/book/delete/{id}")
  public ResponseEntity<Object> delete(@PathVariable("id") Long id) {
    bookService.delete(id);
    return ResponseEntity.ok().build();
  }

  @PatchMapping("/book/update/{id}")
  public ResponseEntity<BookDTO> update(@Valid @RequestBody BookRequest bookRequest,
      @PathVariable("id") Long id) {
    BookDTO bookDTO = bookService.update(bookRequest, id);
    return ResponseEntity.ok(bookDTO);
  }

  /**
   * 책 검색
   */
  @GetMapping("/book/search")
  public ResponseEntity<BookSearchDTO> search(@RequestParam("query") String keyword) {
    BookSearchDTO bookSearchDTO = bookService.search(keyword);
    return ResponseEntity.ok(bookSearchDTO);
  }
}
