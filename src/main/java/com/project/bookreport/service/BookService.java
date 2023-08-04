package com.project.bookreport.service;

import com.project.bookreport.domain.Book;
import com.project.bookreport.model.book.BookCreateRequest;
import com.project.bookreport.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

    public Book create(BookCreateRequest bookCreateRequest){
        Book book = Book.builder()
                .bookName(bookCreateRequest.getBookName())
                .author(bookCreateRequest.getAuthor())
                .publisher(bookCreateRequest.getPublisher())
                .build();
        return bookRepository.save(book);
    }
}
