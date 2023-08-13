package com.project.bookreport.controller;

import com.project.bookreport.model.book.BookDTO;
import com.project.bookreport.model.book.BookRequest;
import com.project.bookreport.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
}
