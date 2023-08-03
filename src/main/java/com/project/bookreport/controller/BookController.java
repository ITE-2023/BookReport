package com.project.bookreport.controller;

import com.project.bookreport.domain.Book;
import com.project.bookreport.domain.Report;
import com.project.bookreport.model.book.BookCreateRequest;
import com.project.bookreport.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @PostMapping("/book/create")
    public ResponseEntity<Book> create(@Valid @RequestBody BookCreateRequest bookCreateRequest
                                        ,@AuthenticationPrincipal Report report){
        Book book = bookService.create(bookCreateRequest, report);
        return ResponseEntity.ok(book);
    }
}
