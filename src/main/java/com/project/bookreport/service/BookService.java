package com.project.bookreport.service;

import com.project.bookreport.domain.Book;
import com.project.bookreport.model.book.BookDTO;
import com.project.bookreport.model.book.BookRequest;
import com.project.bookreport.repository.BookRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

    public BookDTO create(BookRequest bookRequest){
        Optional<Book> originBook = getBook(bookRequest);
        Book saveBook;
        if (originBook.isEmpty()) {
            Book book = Book.builder()
                .bookName(bookRequest.getBookName())
                .author(bookRequest.getAuthor())
                .publisher(bookRequest.getPublisher())
                .build();
            saveBook = bookRepository.save(book);
        } else {
            saveBook = originBook.get();
        }
        return BookDTO.builder()
            .id(saveBook.getId())
            .bookName(saveBook.getBookName())
            .author(saveBook.getAuthor())
            .publisher(saveBook.getPublisher())
            .createDate(saveBook.getCreateDate())
            .updateDate(saveBook.getUpdateDate())
            .build();
    }

    private Optional<Book> getBook(BookRequest bookRequest) {
        return bookRepository.findByBookNameAndAuthorAndPublisher(
            bookRequest.getBookName(),
            bookRequest.getAuthor(), bookRequest.getPublisher());
    }
}

